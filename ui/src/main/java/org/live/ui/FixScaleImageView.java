package org.live.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 可设置固定高宽比的ImageView
 */
public class FixScaleImageView extends AppCompatImageView {

    /**
     * 图片 高/宽比例，为0时不做任何处理
     */
    private float heightWidthRate = 0.0f;

    private boolean widthStandard = true; // 默认以宽度为基准, true: 宽度为基准值,动态计算出高度

    public FixScaleImageView(Context context) {
        this(context, null);
    }

    public FixScaleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FixScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FixScaleImageView, 0, 0);
        this.heightWidthRate = a.getFloat(R.styleable.FixScaleImageView_heightWidthRate, 0.0f);
        widthStandard = a.getBoolean(R.styleable.FixScaleImageView_widthStandard,false);
        a.recycle();
    }

    public void setHeightWidthRate(float rate) {
        this.heightWidthRate = rate;
        if (this.heightWidthRate > 0.0f) {
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (heightWidthRate > 0.0f) {
            if (widthStandard) {
                int viewWidth = getMeasuredWidth();
                int viewHeight = (int) (viewWidth * heightWidthRate + 0.5f);
                setMeasuredDimension(viewWidth, viewHeight);
            } else {
                int viewHeight = getMeasuredHeight();
                int viewWidth = (int) (viewHeight / heightWidthRate + 0.5f);
                setMeasuredDimension(viewWidth, viewHeight);
            }
        }
    }
}