package org.live.test.activity

import android.widget.TextView
import kotlinx.android.synthetic.main.activity_toast.*
import org.live.test.R
import org.live.test.base.BaseActivity
import org.live.test.widget.ToastTipWindowDelay

/**
 * Created by wl on 2018/11/28.
 *
 * 下面两个获取的值是一样的: 如v_layout是布局的第一个元素 获取的值是: [0,55] 说明是包含了状态栏(55)的.
 * v_layout.getLocationInWindow(locations) [0,55]
 * v_layout.getLocationOnScreen(locations) [0,55]
 *
 * 但需要注意: 上面的获取同view的宽高一样都必须在onMeasure之后调用(setContentView中是不能直接获取的)
 *
 * 结合 DecorViewActivity 中提到的方式很容易动态添加布局.
 */
class ToastActivity : BaseActivity() {

    private val toastTipWindow = ToastTipWindowDelay()

    override fun initView() {
        val textView = TextView(this)
        textView.text = "hello world"
        textView.setTextColor(resources.getColor(R.color.white))
        textView.setBackgroundColor(resources.getColor(R.color.red))

        button_show.setOnClickListener {
            toastTipWindow.setPopView(this, v_layout, textView)
            toastTipWindow.show()
        }

        button_hide.setOnClickListener {
            toastTipWindow.setPopView(this, v_layout, textView)
            toastTipWindow.hide()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_toast
    }

}