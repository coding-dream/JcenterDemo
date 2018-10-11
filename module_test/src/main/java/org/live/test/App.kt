package org.live.test

import android.app.Application
import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * Created by wl on 2018/10/2.
 */
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        application = this
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    companion object {
        private lateinit var application: Application
        @JvmStatic
        fun getAppContext(): Context {
            return application
        }
    }
}