package org.live.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.live.baselib.util.SystemUtils;

/**
 * Created by wangli on 2018/6/24.
 */
public class LoadingView_V1 extends FrameLayout {

    private Context mContext;
    private float centerSize;
    private float roundCorner;
    private ObjectAnimator objectAnimator;
    private boolean isShow = false;
    private ImageView imageView;

    public LoadingView_V1(Context context) {
        this(context, null);
    }

    public LoadingView_V1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        centerSize = SystemUtils.dip2px(mContext, 100);
        roundCorner = SystemUtils.dip2px(mContext, 8);
        // 令 onDraw 生效
        setBackgroundResource(R.color.transparent);
        addLoadingIcon();
    }

    public void show() {
        if (!isShow) {
            ((FrameLayout) ((Activity) mContext).getWindow().getDecorView()).addView(this);
            startAnimator();
            isShow = true;
        }
    }

    public void hide() {
        if (isShow) {
            ((FrameLayout) ((Activity) mContext).getWindow().getDecorView()).removeView(this);
            stopAnimator();
            isShow = false;
        }
    }

    private Bitmap drawBackground() {
        // 蒙层画笔
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int bgColor = getResources().getColor(R.color.loading_bg);
        bgPaint.setColor(bgColor);
        canvas.drawRect(0, 0, getWidth(), getHeight(), bgPaint);
        return bitmap;
    }

    private Bitmap drawCenterRect() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int bgColor = getResources().getColor(R.color.loading_center);
        rectPaint.setColor(bgColor);
        canvas.drawRoundRect(new RectF(0, 0, centerSize, centerSize), roundCorner, roundCorner, rectPaint);
        return bitmap;
    }

    private void startAnimator() {
        stopAnimator();
        objectAnimator = ObjectAnimator.ofFloat(imageView, View.ROTATION, 0, 360);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    private void stopAnimator() {
        if (objectAnimator != null && objectAnimator.isRunning()) {
            objectAnimator.cancel();
            objectAnimator = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap background = drawBackground();
        Bitmap centerRect = drawCenterRect();
        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawBitmap(centerRect, getWidth() / 2 - centerSize / 2, getHeight() / 2 - centerSize / 2, null);
    }

    /**
     * 遮罩层不可点击,防止焦点改变重绘 如: 点击button多次
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private void addLoadingIcon() {
        imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.ic_rank_loading);

        int width = SystemUtils.dip2px(mContext, 40);
        LayoutParams lp = new LayoutParams(width, width);
        lp.gravity = Gravity.CENTER;
        addView(imageView, lp);
    }
}
