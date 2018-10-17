package org.live.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import org.live.baselib.util.SystemUtils;

/**
 * Created by wl on 2018/10/17.
 */
public class MyIndicator extends LinearLayout {

    private int DEFAULT_COUNT = 3;

    private int mSingleWidth;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mTabCount = DEFAULT_COUNT;

    private ViewPager mViewPager;
    private float mStartOffset;
    private int mIndicatorColor ;

    private float mIndicatorWidth = 100;
    private float mIndicatorHeight = SystemUtils.dip2px(getContext(), 6);

    public MyIndicator(Context context) {
        super(context);
    }

    public MyIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }
    private ViewPager.OnPageChangeListener mListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mStartOffset = mSingleWidth * (position + positionOffset);
            invalidate();
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, (int) mIndicatorHeight);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        mSingleWidth = SystemUtils.getScreenWidth(context) / DEFAULT_COUNT - SystemUtils.dip2px(context, 10);

        TypedArray attribute = context.obtainStyledAttributes(attrs, R.styleable.MyTabLayout);
        mIndicatorWidth = attribute.getDimension(R.styleable.MyTabLayout_indicatorWidth, mIndicatorWidth);
        mIndicatorHeight = attribute.getDimension(R.styleable.MyTabLayout_indicatorHeight, mIndicatorHeight);
        mIndicatorColor = attribute.getColor(R.styleable.MyTabLayout_indicatorColor, Color.parseColor("#db0a71e1"));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        mPaint.setColor(mIndicatorColor);
        float center = mSingleWidth / 2 + mStartOffset;
        canvas.drawRect(center - mIndicatorWidth / 2, 0, center + mIndicatorWidth / 2, 10, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mViewPager != null) {
            mViewPager.removeOnPageChangeListener(mListener);
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).setOnClickListener(null);
            }
        }
    }

    public void attachToViewPager(@NonNull ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(mListener);
    }
}
