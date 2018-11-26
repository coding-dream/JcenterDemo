package org.live.test.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by wl on 2018/11/26.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
    }

    abstract fun initView()

    abstract fun getLayoutId(): Int
}