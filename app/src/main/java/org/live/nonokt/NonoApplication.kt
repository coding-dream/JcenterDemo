package org.live.nonokt

import android.app.Application

/**
 * Created by wl on 2018/9/5.
 */
class NonoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initSDK()
    }

    private fun initSDK() {
        if ("isModule" == BuildConfig.plugin_type) {
            println("application")
        } else {
            println("library")
        }
    }
}