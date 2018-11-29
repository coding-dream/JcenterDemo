package org.live.baselib.util;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wl on 2018/11/29.
 */
public class TouchHelper {

    /**
     * 模拟点击，点击该View的xy坐标(5,5)
     *
     * https://www.zhihu.com/question/33636939/answer/356059970
     * @param view 模拟点击的View
     */
    public static void simulateTouch(View view) {

        final long downTime = SystemClock.uptimeMillis();
        int[] ints = new int[2];
        view.getLocationOnScreen(ints);
        view.dispatchTouchEvent(MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN,
                ints[0] + 5, ints[1] + 5, 0));
        view.dispatchTouchEvent(MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_UP,
                ints[0] + 5, ints[1] + 5, 0));

    }
}
