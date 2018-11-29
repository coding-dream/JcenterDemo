package org.live.test.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by wl on 2018/11/26.
 */
abstract class BaseActivity : AppCompatActivity() {

    var isAlive = true

    private var unBinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        unBinder = ButterKnife.bind(this)
        initView()
    }

    abstract fun initView()

    abstract fun getLayoutId(): Int

    override fun onDestroy() {
        super.onDestroy()
        isAlive = false
        unBinder?.unbind()
    }
}