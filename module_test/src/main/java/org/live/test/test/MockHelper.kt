package org.live.test.test

import org.live.ui.MenuItem

/**
 * Created by wl on 2018/11/30.
 */
object MockHelper {

    @JvmStatic
    fun getMenuDatas(): MutableList<MenuItem> {
        val datas = mutableListOf<MenuItem>()
        for (i in 0..20) {
            val menuItem = MenuItem("menu: $i")
            datas.add(menuItem)
        }
        return datas
    }
}