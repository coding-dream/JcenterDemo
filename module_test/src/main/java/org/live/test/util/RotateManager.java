package org.live.test.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.view.OrientationEventListener;

import org.live.test.App;
import org.live.test.database.Logger;

/**
 * Created by wl on 2018/11/14.
 */
public class RotateManager {

    private static final int MSG_PHONE_ORIENTATION_CHANGED = 10011;

    private InnerOrientationEventListener mOrientationEventListener;

    private OnOrientationChangeListener mOnOrientationChangeListener;

    public interface OnOrientationChangeListener {
        void onLandscape();

        void onPortrait();
    }

    private WeakHandler handler = new WeakHandler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_PHONE_ORIENTATION_CHANGED:
                    if (mOnOrientationChangeListener != null) {
                        if (isPhoneLandscape()) {
                            mOnOrientationChangeListener.onLandscape();
                        } else {
                            mOnOrientationChangeListener.onPortrait();
                        }
                    }
                    break;
            }
            return true;
        }
    });

    /**
     * 手机方向状态监听
     */
    private class InnerOrientationEventListener extends OrientationEventListener {
        private int mOrientation;

        InnerOrientationEventListener(Context context) {
            super(context);
        }

        boolean isPhoneLandscape() {
            return mOrientation == 90 || mOrientation == 270;
        }

        @Override
        public void onOrientationChanged(int orientation) {
            // 不能旋转时不处理
            if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN || !canRotate() || mOnOrientationChangeListener == null) {
                Logger.d("=======> can't rotate!");
                return;
            }

            // mOrientation: 0/180为竖屏；90/270为横屏
            int newOrientation = ((orientation + 45) / 90 * 90) % 360;
            if (mOrientation != newOrientation) {
                mOrientation = newOrientation;
                handler.removeMessages(MSG_PHONE_ORIENTATION_CHANGED);
                handler.sendEmptyMessageDelayed(MSG_PHONE_ORIENTATION_CHANGED, 800);
            }
        }
    }

    public boolean canRotate() {
        // 使用Application的Context
        final Context appContext = App.getAppContext();
        // 系统旋转开启
        return isAutoRotationEnable(appContext);
    }

    public boolean isPhoneLandscape() {
        return mOrientationEventListener != null && mOrientationEventListener.isPhoneLandscape();
    }

    public void register(Context context, OnOrientationChangeListener onOrientationChangeListener) {
        this.mOnOrientationChangeListener = onOrientationChangeListener;

        mOrientationEventListener = new InnerOrientationEventListener(context);
        if (mOrientationEventListener.canDetectOrientation()) {
            mOrientationEventListener.enable();
        }
    }

    public void unRegister() {
        mOnOrientationChangeListener = null;
        if (mOrientationEventListener != null && mOrientationEventListener.canDetectOrientation()) {
            mOrientationEventListener.disable();
        }
    }

    public static boolean isAutoRotationEnable(Context context) {
        int state = getAutoRotationState(context);
        return state == 1;
    }

    private static int getAutoRotationState(Context context) {
        int sensorState = 0;
        try {
            sensorState = Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
            return sensorState;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensorState;
    }
}
