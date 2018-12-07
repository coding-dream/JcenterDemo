package org.live.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by wl on 2018/11/28.
 */
public class ZZPlaybackPlayer extends ZZVideoPlayer {

    public ZZPlaybackPlayer(@NonNull Context context) {
        super(context);
    }

    public ZZPlaybackPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isLock() {
        if (mController != null) {
            return mController.isLock();
        }
        return false;
    }

    public void showLock() {
        if (mController != null) {
            mController.showLock();
        }
    }

    public void showFlowTips() {
        if (mController != null && isPlaying() || isBufferingPlaying()) {
            mController.showFlowTips();
        }
    }
}