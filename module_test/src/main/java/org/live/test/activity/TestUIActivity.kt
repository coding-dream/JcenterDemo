package org.live.test.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
        toggleButton.isChecked = true

        toggleButton.setOnClickListener {
            val isChecked = toggleButton.isChecked
            toast("isChecked: $isChecked")
        }
    }
}