package org.live.simple.database;

import android.util.Log;

/**
 * Created by wl on 2018/10/10.
 */
public class Logger {

    private static final String TAG = Logger.class.getSimpleName();

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

}