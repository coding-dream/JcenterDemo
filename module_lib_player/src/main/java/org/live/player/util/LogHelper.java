package org.live.player.util;

import android.util.Log;

/**
 * Created by wl on 2018/12/7.
 */
public class LogHelper {

    private static final String TAG = LogHelper.class.getSimpleName();

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }
}