package org.live.test.util;

import android.os.CountDownTimer;

/**
 * Created by wl on 2018/11/29.
 */
public class CountDownHelper {

    private CountDownTimer mCountDownTimer;

    public interface Callback {
        void done(long millits);
    }

    public static CountDownHelper startTimer(int totolTime, int intervalTime, Callback callback) {
        CountDownHelper countDownHelper = new CountDownHelper();
        // 取消定时器
        cancelTimer(countDownHelper);
        countDownHelper.mCountDownTimer = new CountDownTimer(totolTime, intervalTime) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (callback != null) {
                    callback.done(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                if (callback != null) {
                    callback.done(0);
                }
            }
        };
        countDownHelper.mCountDownTimer.start();
        return countDownHelper;
    }

    public static void cancelTimer(CountDownHelper countDownHelper) {
        if (countDownHelper.mCountDownTimer != null) {
            countDownHelper.mCountDownTimer.cancel();
        }
    }
}
