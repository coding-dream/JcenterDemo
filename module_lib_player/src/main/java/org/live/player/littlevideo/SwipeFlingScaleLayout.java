package org.live.player.littlevideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;
import android.widget.Toast;

import org.live.player.R;
import org.live.player.util.LogHelper;

/**
 * Created by wl on 2018/12/5.
 */
public class SwipeFlingScaleLayout extends FrameLayout {

    private final float maxHeight;

    private ViewPager viewPager;
    private View layoutRoot;

    private GestureDetectorCompat gestureDetectorCompat;
    private Scroller mScroller;

    private boolean isScaleView;
    private boolean judgeOrientation;

    private boolean firstScroll = true;
    private View swipeFlingScaleLayout;

    private SwipeCallback swipeCallback;

    private int mLastX;
    private int mLastY;

    public interface SwipeCallback {
        void dismiss(float scrollX, float scrollY, float width, float height);
    }

    public SwipeFlingScaleLayout(@NonNull Context context) {
        this(context, null);
    }

    public SwipeFlingScaleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        maxHeight = displayMetrics.heightPixels;
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        // 参考源码: 这种inflate的方式相当于把 R.layout.widget_swipe_fling_scale 当做子View添加到 SwipeFlingScaleLayout 中.
        // 所以scrollBy()就是对R.layout.widget_swipe_fling_scale整体进行平移.
        swipeFlingScaleLayout = inflate(getContext(), R.layout.widget_swipe_fling_scale, this);
        layoutRoot = findViewById(R.id.layout_root);

        viewPager = findViewById(R.id.viewPager);
        gestureDetectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                super.onShowPress(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                // 注意处理第一次 distanceX,distanceY 第一次的值不具备参考价值
                // (比如我们自己在onTouchEvent中判定的时候, 第一次的LastX,lastY还未赋值, 无法与e2相减).
                if (firstScroll) {
                    LogHelper.d("first Scroll");
                    firstScroll = false;
                    return true;
                }
                if (distanceY < 0 && !judgeOrientation) {
                    isScaleView = true;
                    judgeOrientation = true;
                }
                if (isScaleView) {
                    handleScale(distanceX, distanceY);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }

        });

        // 解决长按屏幕后无法拖动的问题
        gestureDetectorCompat.setIsLongpressEnabled(false);
    }

    public void setViewPagerData(FragmentStatePagerAdapter fragmentStatePagerAdapter) {
        viewPager.setAdapter(fragmentStatePagerAdapter);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = false;
        LogHelper.d("onInterceptTouchEvent");
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                LogHelper.d("ACTION MOVE: " + Math.abs(deltaX) + " " + Math.abs(deltaY));

                if (Math.abs(deltaY) > Math.abs(deltaX) && Math.abs(deltaY) > 20) {
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_UP:
                    firstScroll = true;
                    isScaleView = false;
                    judgeOrientation = false;

                    dismissMe();
                    break;
                case MotionEvent.ACTION_MOVE:
                    return gestureDetectorCompat.onTouchEvent(event);
                default:
                    break;
            }
        return true;
    }

    private void handleScale(float deltaX, float deltaY) {
        // 处理scale缩放
        float moveLength = Math.abs(getScrollY());
        if (getScrollY() < 0) {
            float scaleRadio = moveLength / maxHeight;
            layoutRoot.setAlpha(1- scaleRadio);
            layoutRoot.setScaleX(1 - scaleRadio);
            layoutRoot.setScaleY(1 - scaleRadio);
        }
        scrollBy((int) deltaX, (int) deltaY);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    private void dismissMe() {
        float moveLength = Math.abs(getScrollY());
        if (moveLength > 200) {
            if (swipeCallback != null) {
                float width = layoutRoot.getWidth() * layoutRoot.getScaleX();
                float height = layoutRoot.getHeight() * layoutRoot.getScrollY();

                int scrollX = getScrollX();
                int scrollY = getScrollY();
                swipeCallback.dismiss(scrollX, scrollY, width, height);
            }
        } else {
            resetOriginLayout();
        }
    }

    private void resetOriginLayout() {
        layoutRoot.setAlpha(1);
        layoutRoot.setScaleX(1);
        layoutRoot.setScaleY(1);

        mScroller.startScroll(0, 0, 0, 0, 1000);
        invalidate();
    }

    public void setSwipeCallback(SwipeFlingScaleLayout.SwipeCallback swipeCallback) {
        this.swipeCallback = swipeCallback;
    }

    public void toast(String message) {
        Context context = getContext();
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
