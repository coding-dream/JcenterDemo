package org.live.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.live.baselib.shape.DrawableCreator
import org.live.baselib.shape.TextColorHelper
import org.live.baselib.shape.into
import org.live.baselib.toast

/**
 * Created by wl on 2018/9/5.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawableCreator = DrawableCreator()
        val drawableRed = drawableCreator.createColorDrawable(this, R.color.bg_red)
        val drawableBlue = drawableCreator.createColorDrawable(this, R.color.bg_blue)
        val drawableSelector = drawableCreator.createSelectorDrawable(drawableRed, drawableBlue)
        button.into(drawableSelector)

        tv_msg.setOnClickListener { toast("click me") }
        tv_msg.setTextColor(TextColorHelper.createColorStateList(this, R.color.bg_red, R.color.bg_blue))

    }
}