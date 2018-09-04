package org.live.jtool.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.live.jtool.CommonApp;

/**
 * Created by wangli on 2018/5/24
 */
class AppUtils {

    /**
     * 判断当前网络是否可以使用
     */
    public static boolean isNetworkOK() {
        boolean result = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) CommonApp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    result = true;
                }
            }
        } catch (NoSuchFieldError e) {
            e.printStackTrace();
        }

        return result;
    }
}