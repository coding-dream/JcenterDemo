package org.live.test.activity;

import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.live.test.R;
import org.live.test.base.BaseActivity;
import org.live.test.util.LogHelper;

import butterknife.BindView;

/**
 * Created by wl on 2018/12/5.
 */
public class GestureDetectorActivity extends BaseActivity {

    private static final String TAG = GestureDetectorActivity.class.getSimpleName();

    // fling 的最小距离100px
    private static final float FLING_MIN_DISTANCE = 100;
    // fling 的最小速度 100px/s
    private static final float FLING_MIN_VELOCITY = 100;

    @BindView(R.id.rootLayout)
    View rootLayout;

    @Override
    public void initView() {
        GestureDetectorCompat gestureDetectorCompat = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener(){

            /**
             * OnDoubleTapListener监听器
             *
             * @param e 事件
             * @return 事件消费
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // TODO 常用
                // 单击事件(和onDoubleTap相对, 只会单独触发(单击, 双击, 长按 这三种事件只会单独触发) )
                LogHelper.d(TAG, "onSingleTapConfirmed");
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // TODO 常用
                // 双击事件(和onSingleTapConfirmed相对, 只会单独触发(单击, 双击, 长按 这三种事件只会单独触发))
                LogHelper.d(TAG, "onDoubleTap");
                return super.onDoubleTap(e);
            }

            /** OnGestureListener的内容 */
            @Override
            public boolean onDown(MotionEvent e) {
                // TODO 重要(事件拦截机制 ACTION_DOWN)
                // 等价于 ACTION_DOWN, 每次都会触发.
                // 注意: 如果onTouch的返回值采用GestureDetectorCompat的返回值, 那么ACTION_DOWN第一个事件一定要返回true -> onDown必须返回true.
                LogHelper.d(TAG, "onDown");
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // TODO 常用
                // 可以在拖动屏幕布局需求时替代onDown
                // 与onDown区别: 用户按下的时间超过瞬间(如: 用户按下没有立即松开, 或者按下后拖动布局的)均会触发, 用此函数替代onDown体验更佳.
                LogHelper.d(TAG, "onShowPress");
                super.onShowPress(e);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // 顾名思义: 一次单击的抬起操作(这个函数用的很少, 可以选择不用)

                // 这个函数onSingleTapUp和onSingleTapConfirmed容易混淆.
                // 但是这个函数: 轻击(立刻抬起来)才会触发
                // 场景: 1. 轻击屏幕快速抬起 2. 双击(第一次轻击触发一次) 3. 单击(仅仅是单击,没有其他操作)
                // 不会触发: 1. 双击(第二次击屏幕) 2. 第一次击屏有滑动

                LogHelper.d(TAG, "onSingleTapUp");
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                // onDown -> onScroll -> onScroll -> onScroll
                LogHelper.d(TAG, "onScroll");
                // distanceX 和 distanceY 一般情况下和 e1.getX - e2.getX 的一致, e1是ACTION_DOWN的初次按下的值)
                // distanceX 是 (上次事件坐标 - 当前事件坐标)
                // (手指)向右滑动为负值, 手指向左滑动为正值
                // (手指)向下滑动为负值, 手指向上滑动为正值

                // 注意: onScroll计算得道的距离和我们自己在onTouch里面计算得到的有细微的差别. 不过影响不大.
                // 注意处理第一次 distanceX,distanceY 第一次的值不具备参考价值(即忽略处理第一次onScroll)(比如我们自己在onTouchEvent中判定的时候, 第一次的LastX,lastY还未赋值, 无法与e2相减).
                // 多数情况下, 我们不用GestureDetectorCompat提供的这个方法, 用onTouchEvent里面处理更好.

                // 注意一个闪动的Bug, 即如果整个控件重写的是onTouchEvent(),那么event.getX() 和event.getRawX()均可.
                // 但是如果是customView.setOnTouchListener(), distanceX + distanceY计算用 event.getRawX() + event.getRawY(), 否则会出现闪动Bug.
                // 同理, 委派给GestureDetectorCompat的时候就要在onTouchEvent里面委派而不是onTouchListener中委派(因为一边计算自己距离父控件的事件距离,一边让父控件移动自己, 必然造成闪烁).

                // 注意: 通过scrollBy方式移动子View, View.getLeft(), View.getTop()不会改变.
                LogHelper.d(TAG, "distanceX: " + distanceX);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                // TODO 常用
                // 长按事件(只会单独触发(单击, 双击, 长按 这三种事件只会单独触发))
                LogHelper.d(TAG, "onLongPress");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // TODO 常用
                // 快速fling屏幕, 松手后只会触发一次
                // onDown -> onScroll -> onScroll -> onScroll -> onScroll -> onFling
                LogHelper.d(TAG, "onFling");

                if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                    // 向左滑动
                    LogHelper.d(TAG, "left fling");
                } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                    // 向右滑动
                    LogHelper.d(TAG, "right fling");
                }
                return true;
            }
        });
        // 解决长按屏幕后无法拖动的问题(禁用长按事件)
        gestureDetectorCompat.setIsLongpressEnabled(false);
        // 恢复长按事件
        // gestureDetectorCompat.setIsLongpressEnabled(true);

        rootLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LogHelper.d(TAG, "longClick");
                return false;
            }
        });

        rootLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 每个事件先经过onTouch, 然后才交给GestureDetectorCompat
                LogHelper.d(TAG, "onTouch: " + event.getAction());
                // 尽量委托给gestureDetectorCompat返回true, 否则根据之前经验, 如果ACTION_DOWN事件返回false,
                // 后面的事件就无法交给onTouch了, 进而无法委托给gestureDetectorCompat了.
                // 所以第一个事件ACTION_DOWN一定要返回true().
                // 结论: 如果onTouch事件返回值采用下面的写法: 那么 GestureDetectorCompat.onDown一定要返回true.
                return gestureDetectorCompat.onTouchEvent(event);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_default;
    }
}