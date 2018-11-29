package org.live.test.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.HashMap;

/**
 * Created by wl on 2018/11/28.
 */
public class AnimatorSupport {

    private HashMap<View, ObjectAnimator> animatorHashMap = new HashMap<>();

    /**
     * 透明度动画支持
     * @param isShow
     * @param context
     * @param view
     * @param from
     * @param to
     * @return
     */
    public ObjectAnimator layoutAlphaAnimation(boolean isShow, Context context, final View view, int from, int to) {
        if (view == null) {
            return null;
        }

        clearBeforeAnimation(view);
        final int visibility = view.getVisibility();
        final float y = view.getY();
        if (isShow) {
            view.setVisibility(View.VISIBLE);
        } else {
            if (visibility == View.GONE) {
                return null;
            }
        }
        AnimatorUtil.enableHardware(context, view);
        final ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", from, to);
        animator.setDuration(200);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    view.setVisibility(View.GONE);
                    view.setY(y);
                }
                AnimatorUtil.disableHardware(context, view);
                animatorHashMap.remove(view);
            }
        });
        animator.start();

        animatorHashMap.put(view, animator);
        return animator;
    }

    /**
     * 渐进式动画支持
     * @param isShow
     * @param context
     * @param view
     * @param fromY
     * @param toY
     * @return
     */
    public ObjectAnimator layoutInOutAnimation(final boolean isShow, final Context context, final View view, int fromY, int toY) {
        if (view == null) {
            return null;
        }
        clearBeforeAnimation(view);

        final int visibility = view.getVisibility();
        final float y = view.getY();
        if (isShow) {
            view.setVisibility(View.VISIBLE);
        } else {
            if (visibility == View.GONE) {
                return null;
            }
        }
        AnimatorUtil.enableHardware(context, view);
        final ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", fromY, toY);
        animator.setDuration(200);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isShow) {
                    view.setVisibility(View.GONE);
                    view.setY(y);
                }
                AnimatorUtil.disableHardware(context, view);
                animatorHashMap.remove(view);
            }
        });
        animator.start();
        animatorHashMap.put(view, animator);
        return animator;
    }

    private void clearBeforeAnimation(View view) {
        ObjectAnimator objectAnimator = animatorHashMap.get(view);
        if (objectAnimator != null) {
            objectAnimator.cancel();
            objectAnimator.removeAllListeners();
            // 从集合中移除动画对象
            animatorHashMap.remove(view);
        }
    }
}
