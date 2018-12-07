package org.live.player;

import java.util.Map;

public interface IZZVideoPlayer {

    /**
     * 设置播放器类型
     *
     * @param playerType ijk, native
     */
    void setPlayerType(int playerType);

    /**
     * 设置控制器
     *
     * @param controller 控制器
     * @param videoCallback 控制器外部回调, 可传null
     */
    void setController(AbstractVideoController controller, AbstractVideoController.VideoCallback videoCallback);

    void setUp(VideoItem videoItem);

    void setUp(VideoItem videoItem, Map<String, String> headers);

    /**
     * 设置视频数据
     *
     * @param videoItem 视频数据, 新增数据可以修改VideoItem
     * @param headers 自定义header
     * @param continueFromLastPosition 是否记录上次播放指针
     */
    void setUp(VideoItem videoItem, Map<String, String> headers, boolean continueFromLastPosition);

    /**
     * 开始播放
     */
    void start();

    /**
     * 播放完成或播放失败忽略状态强制start, 主要用于seekBar拖动
     */
    void forceStart();

    /**
     * 重新播放，播放器被暂停、播放错误、播放完成后，需要调用此方法重新播放
     */
    void restart();

    /**
     * 暂停播放
     */
    void pause();

    /**
     * seek到制定的位置继续播放
     *
     * @param pos 播放位置
     */
    void seekTo(long pos);

    /**
     * 设置音量
     *
     * @param volume 音量值
     */
    void setVolume(int volume);

    /**
     * 设置播放速度，目前只有IjkPlayer有效果，原生MediaPlayer暂不支持
     *
     * @param speed 播放速度
     */
    void setSpeed(float speed);

    /*********************************
     * 以下9个方法是播放器在当前的播放状态
     **********************************/
    boolean isIdle();
    boolean isPreparing();
    boolean isPrepared();
    boolean isBufferingPlaying();
    boolean isBufferingPaused();
    boolean isPlaying();
    boolean isPaused();
    boolean isError();
    boolean isCompleted();

    /*********************************
     * 以下2个方法是播放器的模式
     **********************************/
    boolean isFullScreen();
    boolean isNormal();

    /**
     * 获取最大音量
     *
     * @return 最大音量值
     */
    int getMaxVolume();

    /**
     * 获取当前音量
     *
     * @return 当前音量值
     */
    int getVolume();

    /**
     * 获取办法给总时长，毫秒
     *
     * @return 视频总时长ms
     */
    long getDuration();

    /**
     * 获取当前播放的位置，毫秒
     *
     * @return 当前播放位置，ms
     */
    long getCurrentPosition();

    /**
     * 获取视频缓冲百分比
     *
     * @return 缓冲白百分比
     */
    int getBufferPercentage();

    /**
     * 获取播放速度
     *
     * @param speed 播放速度
     * @return 播放速度
     */
    float getSpeed(float speed);

    /**
     * 获取网络加载速度
     *
     * @return 网络加载速度
     */
    long getTcpSpeed();

    /**
     * 进入全屏模式
     */
    void enterFullScreen();

    /**
     * 退出全屏模式
     *
     * @return true 退出
     */
    boolean exitFullScreen();

    /**
     * 使用场景: 1. Activity退出 2. 切换清晰度
     *
     * 此处只释放播放器（如果要释放播放器并恢复控制器状态需要调用{@link #release()}方法）
     * 不管是全屏还是Normal状态下控制器的UI都不恢复初始状态
     * 这样以便在当前播放器状态下可以方便的切换不同的清晰度的视频地址
     */
    void releasePlayer();

    /**
     * 释放INonoVideoPlayer，释放后，内部的播放器被释放掉，同时如果在全屏模式下会退出
     * 并且控制器的UI也应该恢复到最初始的状态.
     */
    void release();
}