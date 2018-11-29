package org.live.test.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_object_animator.*
import org.live.test.R
import org.live.test.util.AnimatorSupport

/**
 * Created by wl on 2018/10/22.
 */
class ObjectAnimatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_animator)

        val animatorSupport = AnimatorSupport()

        button.setOnClickListener {
            animatorSupport.layoutInOutAnimation(true, this, tv_icon, 0,100)
        }
    }
}