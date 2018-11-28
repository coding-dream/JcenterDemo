package org.live.test.activity

import kotlinx.android.synthetic.main.activity_bottom_menu.*
import org.live.test.R
import org.live.test.base.BaseActivity
import org.live.ui.MenuItem

/**
 * Created by wl on 2018/11/28.
 */
class BottomMenuActivity : BaseActivity() {

    override fun initView() {
        val menuDatas = mutableListOf<MenuItem>()
        menuDatas.add(MenuItem("menu1"))
        menuDatas.add(MenuItem("menu2"))
        menuDatas.add(MenuItem("menu3"))
        menuDatas.add(MenuItem("menu4"))

        bottomMenuLayout.setDatas(menuDatas, 4)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_bottom_menu
    }
}