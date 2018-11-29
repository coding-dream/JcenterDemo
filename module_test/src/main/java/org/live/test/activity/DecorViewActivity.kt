package org.live.test.activity

import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_decorview.*
import org.live.test.R
import org.live.test.base.BaseActivity

/**
 * Created by wl on 2018/11/26.
 *
 * android.R.id.content是不包含状态栏55px的, 正好配合 ToastActivity 中的 getLocationOnScreen 动态添加布局
 */
class DecorViewActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_decorview
    }

    override fun initView() {
        // not contains: statusBar(55px)
        val vRoot = findViewById<ViewGroup>(android.R.id.content)
        val viewGroup = findViewById<ViewGroup>(R.id.layout_content)

        val textView = TextView(this)
        textView.text = "new Text"
        textView.gravity = Gravity.CENTER
        textView.setTextColor(resources.getColor(R.color.white))
        textView.setBackgroundColor(resources.getColor(R.color.red))
        textView.layoutParams = v_bottom_content.layoutParams

        viewGroup.addView(textView)
    }
}