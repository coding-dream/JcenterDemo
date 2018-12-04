package org.live.test.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import org.live.baselib.util.SystemUtils;
import org.live.test.R;

/**
 * Created by wl on 2018/12/4.
 */
public abstract class BaseDialog extends Dialog {

    protected Context mContext;

    private int mMaxWidth;
    private int mMaxHeight;

    private boolean mPopUpStyle;
    private boolean hasAnimateEnd;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.ZZShadowDialog);
        this.mContext = context;
        initAllAnimators();
    }

    private void initAllAnimators() {
        // 初始化所有的动画, 方便后面直接使用
    }

    public void setPopUpStyle(boolean popUpStyle) {
        this.mPopUpStyle = popUpStyle;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        mMaxWidth = displayMetrics.widthPixels;
        mMaxHeight = displayMetrics.heightPixels - SystemUtils.getStatusBarHeight((Activity) mContext);

        // 最顶层布局, 解决Dialog最外层lp失效的问题
        LinearLayout rootView = new LinearLayout(mContext);
        rootView.setGravity(Gravity.CENTER);

        // 容器布局, 包含所有的子布局
        LinearLayout containerLayout = new LinearLayout(mContext);
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        rootView.addView(containerLayout);

        // 子View
        View childView = onChildCreateView();
        containerLayout.addView(childView);

        if (mPopUpStyle) {
            setContentView(rootView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            setContentView(rootView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    protected abstract View onChildCreateView();

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 这里加入某个View的动画效果
        showWithAnimate();
    }

    private void showWithAnimate() {

    }

    @Override
    public void onDetachedFromWindow() {
        // 取消定时器如: CountDownTimer
        super.onDetachedFromWindow();
    }

    @Override
    public void dismiss() {
        if (hasAnimateEnd) {
            dismissWithAnimate();
        } else {
            dismissWithNoAnimate();
        }
    }

    private void dismissWithAnimate() {
        // 监听动画进度, 然后最后调用 superDismiss()

    }

    private void dismissWithNoAnimate() {
        superDismiss();
    }

    public void superDismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
        }
    }

    /**
     * 在指定位置显示
     *
     * @param x
     * @param y
     */
    public void showAtLocation(int x, int y) {
        // Left Top (坐标原点为右上角)
        int gravity = Gravity.LEFT | Gravity.TOP;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(gravity);
        params.x = x;
        params.y = y;
        // 此方法说明: 在show() 之前处理window参数即可.
        show();
    }

    /**
     * 设置背景是否昏暗
     * @param isDimEnabled
     */
    public void setDimEnable(boolean isDimEnabled) {
        if (isDimEnabled) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    public void anchorView(View anchorView) {
        int x = 0;
        int y = 0;
        if (anchorView != null) {
            int statusBarHeight = SystemUtils.getStatusBarHeight((Activity) mContext) - dp2px(1);

            int[] location = new int[2];
            anchorView.getLocationOnScreen(location);

            x = location[0] + anchorView.getWidth() / 2;
            y = location[1] - statusBarHeight + anchorView.getHeight();
        }
        showAtLocation(x, y);
    }

    protected int dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}