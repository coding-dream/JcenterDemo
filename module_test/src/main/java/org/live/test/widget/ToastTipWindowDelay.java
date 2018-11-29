package org.live.test.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import org.live.baselib.util.SystemUtils;

/**
 * Created by wl on 2018/11/29.
 */
public class ToastTipWindowDelay {

    private int left;
    private int top;

    private boolean hasInit;
    private ViewGroup vRoot;

    private View popView;
    private ObjectAnimator objectAnimator;

    /**
     * 1. 获取锚点View相对于屏幕的坐标点(需要减去 statusBar的高度)
     * 2. 获取父节点(不包含statusBar)
     * @param activity
     * @param anchorView
     */
    private void init(Activity activity, View anchorView) {
        int statusBarHeight = SystemUtils.getStatusBarHeight(activity);

        if (!hasInit) {
            int location[] = new int[2];
            anchorView.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            left = x;
            top = y - statusBarHeight;

            // 初始化ViewGroup
            vRoot = activity.findViewById(android.R.id.content);
        }
        hasInit = true;
    }

    /**
     * todo 重点注意: setPopView方法不能设置在setContentView中, 因为无法直接获得view的宽高. getLocationOnScreen同理.
     *
     * 此方法可以重复调用, 已处理, 无需担心, 每个ToastTipWindow只能设置一个View, 容易维护.
     * @param activity
     * @param anchorView
     * @param childView
     */
    public void setPopView(Activity activity, View anchorView, View childView) {
        if (this.popView == null && childView != null) {
            init(activity, anchorView);
            updateLayout(childView, anchorView.getWidth(), anchorView.getHeight());
            this.popView = childView;
        }
    }

    private void updateLayout(View childView, int width, int height) {
        // 技巧: 设置和锚点View一样的大小, 如果想核心内容居中, 让被添加View的内容居中即可.
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
        lp.leftMargin = left;
        lp.topMargin = top;
        vRoot.addView(childView, lp);
    }

    public void show() {
        if (popView != null) {
            doAnimator(true, popView, 0, 1);
        }
    }

    public void hide() {
        if (popView != null) {
            doAnimator(false, popView, 1, 0);
        }
    }

    public void doAnimator(boolean letShow, View view, int from, int to) {
        clearAnimator();

        int visibility = view.getVisibility();
        if (letShow) {
            view.setVisibility(View.VISIBLE);
        } else {
            if (visibility == View.GONE) {
                return;
            }
        }

        objectAnimator = ObjectAnimator.ofFloat(view, "alpha", from, to);
        objectAnimator.setDuration(300);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // 只处理隐藏即可.
                if (!letShow) {
                    view.setVisibility(View.GONE);
                }
            }
        });
        objectAnimator.start();
    }

    private void clearAnimator() {
        if (objectAnimator != null && objectAnimator.isRunning()) {
            objectAnimator.cancel();
            objectAnimator.removeAllListeners();
            objectAnimator = null;
        }
    }
}