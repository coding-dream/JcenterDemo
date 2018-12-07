package org.live.player;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by wl on 2018/11/28.
 */
public abstract class AbstractVideoController extends FrameLayout {

    protected VideoCallback mVideoCallback;

    public interface VideoCallback {
        void onBack();
        void onPrepared();
    }

    public AbstractVideoController(Context context) {
        super(context);
    }

    public AbstractVideoController(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCallback(VideoCallback videoCallback) {
        this.mVideoCallback = videoCallback;
    }

    /**
     * 核心实现
     * @param mCurrentState
     */
    public abstract void onPlayStateChanged(int mCurrentState);

    public abstract void onPlayModeChanged(int mCurrentMode);

    public abstract void setVideoPlayer(IZZVideoPlayer nonoVideoPlayer);

    public abstract void setVideoItem(VideoItem mVideoItem);

    public abstract void setContinueFromLastPosition(boolean mContinueFromLastPosition);

    public abstract void release();

    /**  Activity 外部调用, 非必须实现, 如果不使用, 无需重写 */
    public abstract boolean isLock();

    public abstract void showLock();

    public abstract void showFlowTips();

}