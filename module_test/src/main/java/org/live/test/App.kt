package org.live.test

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import skin.support.SkinCompatManager

/**
 * Created by wl on 2018/10/2.
 */
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        application = this
        Logger.addLogAdapter(AndroidLogAdapter())

        // 添加自定义加载策略
        SkinCompatManager.withoutActivity(this).loadSkin()

    }

    companion object {
        private lateinit var application: Application

        private val handler = Handler(Looper.getMainLooper())

        @JvmStatic
        fun getAppContext(): Context {
            return application
        }

        @JvmStatic
        fun getHandler(): Handler {
            return handler
        }
    }
}