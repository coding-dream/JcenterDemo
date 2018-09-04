package org.live.jtool;

import android.app.Application;
import android.content.Context;

/**
 * Created by wl on 2018/9/4.
 */
public class CommonApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getAppContext(){
        return context;
    }
}
