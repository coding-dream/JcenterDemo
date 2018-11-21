package org.live.test.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import kotlinx.android.synthetic.main.activity_object_animator.*
import org.live.test.R
import org.live.test.widget.SlowlyInterpolator

/**
 * Created by wl on 2018/10/22.
 */
class ObjectAnimatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_animator)
        button.setOnClickListener {
            tv_icon.alpha = 1f
            tv_icon.animate()
                    .alpha(0f)
                    .setDuration(8000)
                    // 动画差值器的计算公式: 起始值 + (终点值 - ) * 显示进度
                    .setInterpolator(SlowlyInterpolator())
                    .start()
        }
    }
}