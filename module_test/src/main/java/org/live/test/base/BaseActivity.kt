package org.live.test.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import org.live.test.R

/**
 * Created by wl on 2018/11/26.
 */
abstract class BaseActivity : AppCompatActivity() {

    var isAlive = false

    private var unBinder: Unbinder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        isAlive = true
        overridePendingTransitionEnter()

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

    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransitionExit()
    }

    private fun overridePendingTransitionEnter() {
        if (toggleOverridePendingTransition()) {
            overridePendingTransition(R.anim.nn_left_in, R.anim.nn_right_out)
        }
    }

    private fun overridePendingTransitionExit() {
        if (toggleOverridePendingTransition()) {
            overridePendingTransition(R.anim.nn_right_in, R.anim.nn_left_out)
        }
    }

    private fun toggleOverridePendingTransition(): Boolean {
        return true
    }
}
