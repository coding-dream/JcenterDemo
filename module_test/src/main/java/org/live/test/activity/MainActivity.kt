package org.live.test.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.startActivity
import org.live.test.R
import skin.support.SkinCompatManager

/**
 * Created by wl on 2018/9/5.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SkinCompatManager.getInstance().loadSkin("module_skin-debug.skin", SkinCompatManager.SKIN_LOADER_STRATEGY_ASSETS)

        startActivity<PullToRefreshActivity>()
    }
}