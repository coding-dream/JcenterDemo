package org.live.test.activity

import android.view.View
import org.live.test.R
import org.live.test.base.BaseActivity
import org.live.test.util.CountDownHelper

/**
 * Created by wl on 2018/11/29.
 *
 * 注意: 使用LinearLayout等控件, 如果同一个方向的View均march_parent, 那么第二个将没有剩余空间, 除非我们手动设置具体数值, 才能超出屏幕暂未显示.
 */
class OverflowActivity : BaseActivity() {

    private lateinit var childView1: View
    private lateinit var childView2: View

    override fun initView() {
        childView1 = findViewById(R.id.v_child1)
        childView2 = findViewById(R.id.v_child2)

        CountDownHelper.startTimer(30000, 1000) {
            childView1.offsetTopAndBottom(-100)
            childView2.offsetTopAndBottom(-100)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_overflow
    }

}