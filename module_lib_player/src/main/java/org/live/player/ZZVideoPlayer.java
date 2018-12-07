package org.live.player;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Map;

import tv.danmaku.ijk.media.player.AndroidMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by wl on 2018/11/5.
 */
public class ZZVideoPlayer extends FrameLayout implements IZZVideoPlayer {

    public static final String TAG = ZZVideoPlayer.class.getSimpleName();

    /**
     * 播放错误
     **/
    public static final int STATE_ERROR = -1;

    /**
     * 播放未开始
     **/
    public static final int STATE_IDLE = 0;
    /**
     * 播放准备中
     **/
    public static final int STATE_PREPARING = 1;
    /**
     * 播放准备就绪
     **/
    public static final int STATE_PREPARED = 2;
    /**
     * 正在播放
     **/
    public static final int STATE_PLAYING = 3;
    /**
     * 暂停播放
     **/
    public static final int STATE_PAUSED = 4;
    /**
     * 正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，缓冲区数据足够后恢复播放)
     **/
    public static final int STATE_BUFFERING_PLAYING = 5;
    /**
     * 正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，此时暂停播放器，继续缓冲，缓冲区数据足够后恢复暂停
     **/
    public static final int STATE_BUFFERING_PAUSED = 6;
    /**
     * 播放完成
     **/
    public static final int STATE_COMPLETED = 7;

    /**
     * 普通模式
     **/
    public static final int MODE_NORMAL = 10;
    /**
     * 全屏模式
     **/
    public static final int MODE_FULL_SCREEN = 11;

    /**
     * IjkPlayer
     **/
    public static final int TYPE_IJK = 111;
    /**
     * MediaPlayer
     **/
    public static final int TYPE_NATIVE = 222;

    private int mPlayerType = TYPE_IJK;
    private int mCurrentState = STATE_IDLE;
    private int mCurrentMode = MODE_NORMAL;

    private Context mContext;

    private AudioManager mAudioManager;
    private IMediaPlayer mMediaPlayer;
    /**
     * 播放器的容器: 存放mTextureView, mController
     */
    private FrameLayout mContainer;
    private ZZTextureView mTextureView;
    protected AbstractVideoController mController;

    private SurfaceTexture mSurfaceTexture;
    private Surface mSurface;
    private VideoItem mVideoItem;

    private Map<String, String> mHeaders;
    private int mBufferPercentage;
    private boolean mContinueFromLastPosition = false;

    public ZZVideoPlayer(@NonNull Context context) {
        this(context, null);
    }

    public ZZVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void init() {
        mContainer = new FrameLayout(mContext);
        mContainer.setBackgroundColor(Color.BLACK);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mContainer, params);
    }

    @Override
    public void setUp(VideoItem videoItem) {
        setUp(videoItem, null);
    }

    @Override
    public void setUp(VideoItem videoItem, Map<String, String> headers) {
        setUp(videoItem, headers, false);
    }

    @Override
    public void setUp(VideoItem videoItem, Map<String, String> headers, boolean continueFromLastPosition) {
        this.mVideoItem = videoItem;
        this.mHeaders = headers;
        this.mContinueFromLastPosition = continueFromLastPosition;
        Logger.d(TAG, "videoItem: " + videoItem);
        updateController();
    }

    /**
     * 由于用户设置setUp和setController的顺序不确定, 所以调用两次
     */
    private void updateController() {
        // 设置video信息
        if (mController != null && mVideoItem != null) {
            mController.setVideoItem(mVideoItem);
        }
        if (mController != null) {
            mController.setContinueFromLastPosition(mContinueFromLastPosition);
        }
    }

    @Override
    public void setController(AbstractVideoController controller, AbstractVideoController.VideoCallback videoCallback) {
        mContainer.removeView(mController);

        mController = controller;

        if (videoCallback != null) {
            mController.setCallback(videoCallback);
        }
        updateController();

        // 重置布局
        mController.release();
        mController.setVideoPlayer(this);
        // 添加控制器布局
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mContainer.addView(mController, params);
    }

    /**
     * 设置播放器类型
     *
     * @param playerType IjkPlayer or MediaPlayer.
     */
    @Override
    public void setPlayerType(int playerType) {
        mPlayerType = playerType;
    }

    @Override
    public void start() {
        if (mCurrentState == STATE_IDLE) {
            initAudioManager();
            initMediaPlayer();
            initTextureView();
            addTextureView();
        } else {
            Logger.d("只有在mCurrentState == STATE_IDLE时才能调用start方法.");
        }
    }

    private void addTextureView() {
        this.setClipChildren(false);

        mContainer.removeView(mTextureView);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        // 添加播放器布局
        mContainer.addView(mTextureView, 0, params);
    }

    private void initTextureView() {
        if (mTextureView == null) {
            mTextureView = new ZZTextureView(mContext);
            mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                    if (mSurfaceTexture == null) {
                        mSurfaceTexture = surfaceTexture;
                        openMediaPlayer();
                    } else {
                        mTextureView.setSurfaceTexture(mSurfaceTexture);
                    }
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                    // nothing to do
                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    return mSurfaceTexture == null;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                    // nothing to do
                }
            });
        }
    }

    private void initMediaPlayer() {
        if (mMediaPlayer == null) {
            switch (mPlayerType) {
                case TYPE_NATIVE:
                    mMediaPlayer = new AndroidMediaPlayer();
                    break;
                case TYPE_IJK:
                default:
                    mMediaPlayer = new IjkMediaPlayer();
                    // ((IjkMediaPlayer)mMediaPlayer).setOption(1, "analyzemaxduration", 100L);
                    // ((IjkMediaPlayer)mMediaPlayer).setOption(1, "probesize", 10240L);
                    // ((IjkMediaPlayer)mMediaPlayer).setOption(1, "flush_packets", 1L);
                    // ((IjkMediaPlayer)mMediaPlayer).setOption(4, "packet-buffering", 0L);
                    // ((IjkMediaPlayer)mMediaPlayer).setOption(4, "framedrop", 1L);
                    break;
            }
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
    }

    private void initAudioManager() {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
    }

    private void openMediaPlayer() {
        // 屏幕常亮
        mContainer.setKeepScreenOn(true);
        // 设置监听
        mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
        mMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        mMediaPlayer.setOnErrorListener(mOnErrorListener);
        mMediaPlayer.setOnInfoListener(mOnInfoListener);
        mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        // 设置dataSource
        try {
            mMediaPlayer.setDataSource(mContext.getApplicationContext(), Uri.parse(mVideoItem.getUrl()), mHeaders);
            if (mSurface == null) {
                mSurface = new Surface(mSurfaceTexture);
            }
            mMediaPlayer.setSurface(mSurface);
            mMediaPlayer.prepareAsync();
            mCurrentState = STATE_PREPARING;
            mController.onPlayStateChanged(mCurrentState);
            Logger.d("STATE_PREPARING");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e("打开播放器发生错误", e);
        }
    }

    /**
     * 播放完成或播放失败
     */
    @Override
    public void forceStart() {
        mMediaPlayer.start();
        mCurrentState = STATE_PLAYING;
        mController.onPlayStateChanged(mCurrentState);
    }

    @Override
    public void restart() {
        if (mCurrentState == STATE_PAUSED) {
            mMediaPlayer.start();
            mCurrentState = STATE_PLAYING;
            mController.onPlayStateChanged(mCurrentState);
            Logger.d("STATE_PLAYING");
        } else if (mCurrentState == STATE_BUFFERING_PAUSED) {
            mMediaPlayer.start();
            mCurrentState = STATE_BUFFERING_PLAYING;
            mController.onPlayStateChanged(mCurrentState);
            Logger.d("STATE_BUFFERING_PLAYING");
        } else if (mCurrentState == STATE_COMPLETED || mCurrentState == STATE_ERROR) {
            mMediaPlayer.reset();
            openMediaPlayer();
        } else {
            Logger.d("NonoVideoPlayer在mCurrentState == " + mCurrentState + "时不能调用restart()方法.");
        }
    }

    @Override
    public void pause() {
        if (mCurrentState == STATE_PLAYING || mCurrentState == STATE_PREPARING || mCurrentState == STATE_PREPARED) {
            mMediaPlayer.pause();
            mCurrentState = STATE_PAUSED;
            mController.onPlayStateChanged(mCurrentState);
            Logger.d("STATE_PAUSED");
        }
        if (mCurrentState == STATE_BUFFERING_PLAYING) {
            mMediaPlayer.pause();
            mCurrentState = STATE_BUFFERING_PAUSED;
            mController.onPlayStateChanged(mCurrentState);
            Logger.d("STATE_BUFFERING_PAUSED");
        }
    }

    @Override
    public void seekTo(long pos) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(pos);
        }
    }

    @Override
    public void setVolume(int volume) {
        if (mAudioManager != null) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        }
    }

    @Override
    public void setSpeed(float speed) {
        if (mMediaPlayer instanceof IjkMediaPlayer) {
            ((IjkMediaPlayer) mMediaPlayer).setSpeed(speed);
        } else {
            Logger.d("只有IjkPlayer才能设置播放速度");
        }
    }

    /*********************************
     * 以下9个方法是播放器在当前的播放状态
     **********************************/
    @Override
    public boolean isIdle() {
        return mCurrentState == STATE_IDLE;
    }

    @Override
    public boolean isPreparing() {
        return mCurrentState == STATE_PREPARING;
    }

    @Override
    public boolean isPrepared() {
        return mCurrentState == STATE_PREPARED;
    }

    @Override
    public boolean isBufferingPlaying() {
        return mCurrentState == STATE_BUFFERING_PLAYING;
    }

    @Override
    public boolean isBufferingPaused() {
        return mCurrentState == STATE_BUFFERING_PAUSED;
    }

    @Override
    public boolean isPlaying() {
        return mCurrentState == STATE_PLAYING;
    }

    @Override
    public boolean isPaused() {
        return mCurrentState == STATE_PAUSED;
    }

    @Override
    public boolean isError() {
        return mCurrentState == STATE_ERROR;
    }

    @Override
    public boolean isCompleted() {
        return mCurrentState == STATE_COMPLETED;
    }

    /*********************************
     * 以下2个方法是播放器的模式
     **********************************/
    @Override
    public boolean isFullScreen() {
        return mCurrentMode == MODE_FULL_SCREEN;
    }

    @Override
    public boolean isNormal() {
        return mCurrentMode == MODE_NORMAL;
    }

    @Override
    public int getMaxVolume() {
        if (mAudioManager != null) {
            return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        }
        return 0;
    }

    @Override
    public int getVolume() {
        if (mAudioManager != null) {
            return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        }
        return 0;
    }

    @Override
    public long getDuration() {
        return mMediaPlayer != null ? mMediaPlayer.getDuration() : 0;
    }

    @Override
    public long getCurrentPosition() {
        return mMediaPlayer != null ? mMediaPlayer.getCurrentPosition() : 0;
    }

    @Override
    public int getBufferPercentage() {
        return mBufferPercentage;
    }

    @Override
    public float getSpeed(float speed) {
        if (mMediaPlayer instanceof IjkMediaPlayer) {
            return ((IjkMediaPlayer) mMediaPlayer).getSpeed(speed);
        }
        return 0;
    }

    @Override
    public long getTcpSpeed() {
        if (mMediaPlayer instanceof IjkMediaPlayer) {
            return ((IjkMediaPlayer) mMediaPlayer).getTcpSpeed();
        }
        return 0;
    }

    @Override
    public void enterFullScreen() {
        if (mCurrentMode == MODE_FULL_SCREEN) {
            return;
        }
        ZZPlayerUtil.scanForActivity(mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        setFullScreenLayout();
    }

    @Override
    public boolean exitFullScreen() {
        if (mCurrentMode == MODE_FULL_SCREEN) {
            ZZPlayerUtil.scanForActivity(mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setSmallScreenLayout();
            return true;
        }
        return false;
    }

    public void setFullScreenLayout() {
        if (mCurrentMode == MODE_FULL_SCREEN) {
            return;
        }
        // 隐藏ActionBar、状态栏，并横屏
        ZZPlayerUtil.hideActionBar(mContext);

        ViewGroup contentView = (ViewGroup) ZZPlayerUtil.scanForActivity(mContext)
                .findViewById(android.R.id.content);
        this.removeView(mContainer);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        // 父容器contentView (全屏模式):
        contentView.addView(mContainer, params);

        mCurrentMode = MODE_FULL_SCREEN;
        mController.onPlayModeChanged(mCurrentMode);
        Logger.d("MODE_FULL_SCREEN");
    }

    public void setSmallScreenLayout() {
        if (mCurrentMode == MODE_FULL_SCREEN) {
            ViewGroup contentView = (ViewGroup) ZZPlayerUtil.scanForActivity(mContext)
                    .findViewById(android.R.id.content);
            contentView.removeView(mContainer);
            LayoutParams params = new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            // 父容器this(尺寸没有改变过):
            this.addView(mContainer, params);

            mCurrentMode = MODE_NORMAL;
            mController.onPlayModeChanged(mCurrentMode);
            Logger.d("MODE_NORMAL");
        }
    }

    @Override
    public void release() {

        // 退出全屏
        if (isFullScreen()) {
            exitFullScreen();
        }
        mCurrentMode = MODE_NORMAL;

        // 释放播放器
        releasePlayer();

        // 恢复控制器
        if (mController != null) {
            mController.release();
        }
        Runtime.getRuntime().gc();
    }

    /**
     * 释放播放器,在Activity退出的时候调用 {@link ZZVideoPlayer#release()}
     */
    @Override
    public void releasePlayer() {
        savePlayPosition();

        if (mAudioManager != null) {
            mAudioManager.abandonAudioFocus(null);
            mAudioManager = null;
        }
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mContainer.removeView(mTextureView);
        if (mSurface != null) {
            mSurface.release();
            mSurface = null;
        }
        if (mSurfaceTexture != null) {
            mSurfaceTexture.release();
            mSurfaceTexture = null;
        }
        mCurrentState = STATE_IDLE;
    }

    public void savePlayPosition() {
        if (!mContinueFromLastPosition) {
            return;
        }
        // 保存播放位置
        if (isPlaying() || isBufferingPlaying() || isBufferingPaused() || isPaused()) {
            ZZPlayerUtil.savePlayPosition(mContext, mVideoItem.getUrl(), getCurrentPosition());
        } else if (isCompleted()) {
            ZZPlayerUtil.clearPlayPosition(mContext, mVideoItem.getUrl());
        }
    }

    /*********************************
     * 以下是ijk播放器的监听器
     **********************************/
    private IMediaPlayer.OnPreparedListener mOnPreparedListener
            = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {
            mp.start();
            // 优化: 状态位置的调换,以便流量提示可以立即pause.
            mCurrentState = STATE_PREPARED;
            mController.onPlayStateChanged(mCurrentState);

        }
    };

    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener
            = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sar_num, int sar_den) {
            mTextureView.setVideoSize(width, height);
            Logger.d("onVideoSizeChanged ——> width：" + width + "， height：" + height);
        }
    };

    private IMediaPlayer.OnCompletionListener mOnCompletionListener
            = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            mCurrentState = STATE_COMPLETED;
            mController.onPlayStateChanged(mCurrentState);
            Logger.d("onCompletion ——> STATE_COMPLETED");
            // 清除屏幕常亮
            mContainer.setKeepScreenOn(false);
        }
    };

    private IMediaPlayer.OnErrorListener mOnErrorListener
            = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            Logger.d("onError ——> STATE_ERROR ———— what：" + what + ", extra: " + extra);
            // 直播流播放时去调用mediaPlayer.getDuration会导致-38和-2147483648错误，忽略该错误
            if (what != -38 && what != -2147483648 && extra != -38 && extra != -2147483648) {
                mCurrentState = STATE_ERROR;
                mController.onPlayStateChanged(mCurrentState);
            }
            return true;
        }
    };

    private IMediaPlayer.OnInfoListener mOnInfoListener
            = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer mp, int what, int extra) {
            if (what == IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                // 播放器开始渲染
                mCurrentState = STATE_PLAYING;
                mController.onPlayStateChanged(mCurrentState);
                Logger.d("onInfo ——> MEDIA_INFO_VIDEO_RENDERING_START：STATE_PLAYING");
            } else if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_START) {
                // MediaPlayer暂时不播放，以缓冲更多的数据
                // 用户手动触发
                if (mCurrentState == STATE_PAUSED || mCurrentState == STATE_BUFFERING_PAUSED) {
                    mCurrentState = STATE_BUFFERING_PAUSED;
                    Logger.d("onInfo ——> MEDIA_INFO_BUFFERING_START：STATE_BUFFERING_PAUSED");
                } else {
                    mCurrentState = STATE_BUFFERING_PLAYING;
                    Logger.d("onInfo ——> MEDIA_INFO_BUFFERING_START：STATE_BUFFERING_PLAYING");
                }
                mController.onPlayStateChanged(mCurrentState);
            } else if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
                // 填充缓冲区后，MediaPlayer恢复播放/暂停
                if (mCurrentState == STATE_BUFFERING_PLAYING) {
                    mCurrentState = STATE_PLAYING;
                    mController.onPlayStateChanged(mCurrentState);
                    Logger.d("onInfo ——> MEDIA_INFO_BUFFERING_END： STATE_PLAYING");
                }
                // 用户手动触发
                if (mCurrentState == STATE_BUFFERING_PAUSED) {
                    mCurrentState = STATE_PAUSED;
                    mController.onPlayStateChanged(mCurrentState);
                    Logger.d("onInfo ——> MEDIA_INFO_BUFFERING_END： STATE_PAUSED");
                }
            } else if (what == IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED) {
                // 视频旋转了extra度，需要恢复
                if (mTextureView != null) {
                    mTextureView.setRotation(extra);
                    Logger.d("视频旋转角度：" + extra);
                }
            } else if (what == IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE) {
                Logger.d("视频不能seekTo，为直播视频");
            } else {
                Logger.d("onInfo ——> what：" + what);
            }
            return true;
        }
    };

    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener
            = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            mBufferPercentage = percent;
        }
    };

}