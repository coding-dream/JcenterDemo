package org.live.test.activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_tablayout.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.live.test.R

/**
 * Created by wl on 2018/11/4.
 * 这个控件有坑，所以需要记录一下
 */
class TabLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tablayout)
        requestData()
    }

    /**
     * 模拟网络延时请求
     */
    private fun requestData() {
        doAsync {
            Thread.sleep(2000)

            uiThread {
                val fragments = mutableListOf<String>()
                for (i in 0..5) {
                    fragments.add("i: $i")
                }
                // 添加一系列的fragments
                // if (playBackList != null && playBackList.size > 0) {
                //    fragments.add(playBackListFragment)
                // }
                // fragments.add(videoFragment)

                viewPager.adapter = CustomPageAdapter(fragments)
                viewPager.offscreenPageLimit = 10
                tabLayout.setupWithViewPager(viewPager)
                // 设置自定义Tab的标题
                // TODO: 此处一定要注意tabLayout布局中设置的height应为wrap_content, support 28 版本 Indicator显示不出来就是因为height不够.找了很久的坑.
                initCustomTabs(tabLayout)
            }
        }
    }

    private fun initCustomTabs(layout: TabLayout) {
        val tabCount = layout.tabCount
        for (i in 0 until tabCount) {
            setupTabText(layout, i, "Tab: $i")
        }

        layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                (tab.customView!!.findViewById<View>(R.id.tab_text) as TextView).setTextColor(resources.getColor(R.color.default_theme_text_003))
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                (tab.customView!!.findViewById<View>(R.id.tab_text) as TextView).setTextColor(resources.getColor(R.color.default_theme_text_004))
            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    private fun setupTabText(layout: TabLayout, index: Int, title: String) {
        val videoTab = layout.getTabAt(index)
        videoTab?.let {
            videoTab.setCustomView(R.layout.nn_profile_tab_item)
            val itemView = videoTab.customView
            val textView = itemView?.findViewById<TextView>(R.id.tab_text)
            textView?.text = title
        }
    }
}

class CustomPageAdapter(val datas: MutableList<String> = mutableListOf()) : PagerAdapter() {

    private val sparseArray: SparseArray<View> = SparseArray(10)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var cacheView = sparseArray.get(position)
        if (cacheView == null) {
            cacheView = LayoutInflater.from(container.context).inflate(R.layout.listitem_test, container, false)
            sparseArray.put(position, cacheView)
        }
        container.addView(cacheView)
        return cacheView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return datas.size
    }
}