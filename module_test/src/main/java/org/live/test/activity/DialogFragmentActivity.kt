package org.live.test.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_dialog.*
import org.live.baselib.toast
import org.live.baselib.util.SystemUtils
import org.live.test.R
import org.live.test.widget.MenuDialogFragment

/**
 * Created by wl on 2018/11/23.
 *
 * 使用 TreasureBoxRankDialog 标准. 反例: PlaybackMenuDialog
 * 注意: 显示dialog的时候, dismissAllowingStateLoss则没问题(只要是当前的fragment实例)
 * DialogFragment和Dialog都会自动处理back按键(dismissAllowingStateLoss)的, 典型的就是主动处理dismiss: 换肤的处理注意事项
 *
 * if(themeLoadingDialog != null && themeLoadingDialog.isShowing) { it.dismiss() } 参考: BaseActivity
 *
 *  Dialog显示情况下,如果主动调用finish(),测试发现也是不会报错的, 所以不需要我们dismiss,交给系统好了.
 */
class DialogFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        MenuDialogFragment.display(supportFragmentManager, "dong")
        MenuDialogFragment.display(supportFragmentManager, "dong")
        MenuDialogFragment.display(supportFragmentManager, "dong")
    }
}