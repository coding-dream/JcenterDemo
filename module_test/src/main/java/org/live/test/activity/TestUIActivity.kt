package org.live.test.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_ui_test.*
import org.live.baselib.toast
import org.live.test.R

/**
 * Created by wl on 2018/10/17.
 */
class TestUIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui_test)

        findViewById<View>(R.id.tv_back1).setOnClickListener {
            toast("this is back1")
        }
    }
}