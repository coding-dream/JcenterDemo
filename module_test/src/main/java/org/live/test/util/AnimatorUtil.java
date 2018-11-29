package org.live.test.util;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class AnimatorUtil {

    public static void enableHardware(Context context, View view) {
        if (isEnableHardware(context) && view != null) {
            view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
    }

    public static void disableHardware(Context context, View view) {
        if (isEnableHardware(context) && view != null) {
            view.setLayerType(View.LAYER_TYPE_NONE, null);
        }
    }

    public static boolean isEnableHardware(Context context) {
        EasyDeviceInfo deviceInfo = new EasyDeviceInfo(context);
        String manufacturer = deviceInfo.getManufacturer();
        String brand = deviceInfo.getBuildBrand();
        String deviceModel = deviceInfo.getModel();

        return !("OPPO".equals(manufacturer) && "OPPO".equals(brand) && "N5117".equals(deviceModel));
    }

    public static void cancelAnimator(ObjectAnimator animator) {
        if (animator != null) {
            animator.removeAllListeners();
            animator.end();
        }
    }

    /**
     * 渐隐/渐现动画
     *
     * @param view     需要实现动画的对象
     * @param duration 动画实现的时长（ms）
     */
    public static void showOrHiddenAnimation(final View view, boolean isShowView, long duration) {
        if (view == null) return;
        view.clearAnimation();
        float start = 0f;
        float end = 0f;
        if (isShowView) {
            end = 1f;
            view.setVisibility(View.VISIBLE);
        } else {
            start = 1f;
            view.setVisibility(View.GONE);
        }
        AlphaAnimation animation = new AlphaAnimation(start, end);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }
        });
        view.setAnimation(animation);
        animation.start();
    }
}
