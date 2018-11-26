package org.live.simple;

import android.app.Application;
import android.content.Context;

/**
 * Created by wl on 2018/11/26.
 */
public class App extends Application {

    private static Context application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Context getAppContext() {
        return application;
    }
}
