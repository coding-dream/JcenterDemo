package org.live.test.database;

import android.util.Log;

/**
 * Created by wl on 2018/10/10.
 */
public class Logger {

    public static void d(String msg) {
        com.orhanobut.logger.Logger.d(msg);
    }

    public static void d(String tag, String msg) {
        com.orhanobut.logger.Logger.d(tag, msg);
    }

    public static void dd(String tag, String msg) {
        Log.d(tag, msg);
    }
}