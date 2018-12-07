package org.live.player;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by wl on 2018/11/29.
 */
public class SimpleController extends AbstractVideoController {

    public SimpleController(Context context) {
        super(context);
    }

    public SimpleController(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onPlayStateChanged(int mCurrentState) {
        switch (mCurrentState) {
            case ZZVideoPlayer.STATE_PREPARED:
                if (mVideoCallback != null) {
                    mVideoCallback.onPrepared();
                }
                break;
        }
    }

    @Override
    public void onPlayModeChanged(int mCurrentMode) {

    }

    @Override
    public void setVideoPlayer(IZZVideoPlayer nonoVideoPlayer) {

    }

    @Override
    public void setVideoItem(VideoItem mVideoItem) {

    }

    @Override
    public void setContinueFromLastPosition(boolean mContinueFromLastPosition) {

    }

    @Override
    public void release() {

    }

    @Override
    public boolean isLock() {
        // nothing to do
        return false;
    }

    @Override
    public void showLock() {
        // nothing to do
    }

    @Override
    public void showFlowTips() {
        // nothing to do
    }
}
