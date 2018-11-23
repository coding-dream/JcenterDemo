package org.live.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wl on 2018/11/23.
 */
public class LoadingView_V2 extends View {
    private static final int SPEED = 10;
    private Bitmap mBitmap;
    private int mRotate;

    public LoadingView_V2(Context context) {
        this(context, null);
    }

    public LoadingView_V2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_loading);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FaceLoadingView);
        Drawable drawable = typedArray.getDrawable(R.styleable.FaceLoadingView_loading_image);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        if (bitmapDrawable != null) {
            mBitmap = bitmapDrawable.getBitmap();
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mBitmap.getWidth() * 2, mBitmap.getHeight() * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(LoadingView_V2.class.getSimpleName(), "onDraw");
        int rotate = mRotate % 360;
        canvas.rotate(rotate, getWidth() / 2, getHeight() / 2);
        mRotate += SPEED;
        canvas.drawBitmap(mBitmap, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2, null);
        invalidate();
    }

    /**
     * 显示loading
     */
    public void show() {
        setAlpha(1);
        setVisibility(VISIBLE);
    }

    /**
     * 隐藏loading, onDestroy 中无需调用, 会自动回收
     */
    public void hide() {
        ViewCompat.animate(this)
                .alpha(0)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        setVisibility(GONE);
                    }
                })
                .start();
    }
}