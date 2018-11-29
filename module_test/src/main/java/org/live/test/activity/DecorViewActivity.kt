package org.live.test.activity

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.orhanobut.logger.Logger
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
        val toastView1 = initToastView()
        val toastView2 = initToastView()

        addToastView1(toastView1)
        addToastView1(toastView1)
        addToastView1(toastView1)

        addToastView2(toastView2, FrameLayout.LayoutParams(100,100, Gravity.CENTER))
        addToastView2(toastView2, FrameLayout.LayoutParams(100,100, Gravity.CENTER))
        addToastView2(toastView2, FrameLayout.LayoutParams(100,100, Gravity.CENTER))
    }

    private fun initToastView(): TextView {
        val textView = TextView(this)
        textView.text = "new Text"
        textView.gravity = Gravity.CENTER
        textView.setTextColor(resources.getColor(R.color.white))
        textView.setBackgroundColor(resources.getColor(R.color.red))
        textView.layoutParams = v_bottom_content.layoutParams
        return textView
    }

    private fun addToastView1(toastView: View) {
        if (isNotAdded(toastView)) {
            // not contains: statusBar(55px)
            val vRoot = findViewById<ViewGroup>(android.R.id.content)
            val viewGroup = findViewById<ViewGroup>(R.id.layout_content)
            viewGroup.addView(toastView)
            Logger.d("init added")
        } else {
            Logger.d("already added")
        }
    }

    private fun addToastView2(toastView: View, lp: FrameLayout.LayoutParams) {
        if (isNotAdded(toastView)) {
            // 直接在android.R.id.content这个容器(FrameLayout) 添加子View
            addContentView(toastView, lp)
            Logger.d("init added")
        } else {
            Logger.d("already added")
        }
    }

    /**
     * 判断某个View是否已经被添加过
     * 方式二: 遍历ViewGroup的子View, 和当前View对比.
     */
    fun isNotAdded(view: View): Boolean {
        return view.parent == null
    }
}