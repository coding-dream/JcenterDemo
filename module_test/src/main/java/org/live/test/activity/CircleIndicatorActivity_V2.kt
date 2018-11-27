package org.live.test.activity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_circle_indicator_v2.*
import org.live.test.R
import org.live.test.base.BaseActivity
import java.util.*

/**
 * Created by wl on 2018/11/26.
 */
class CircleIndicatorActivity_V2 : BaseActivity() {

    var fragments: MutableList<GuideFragment> = ArrayList()

    override fun initView() {
        fragments.add(GuideFragment())
        fragments.add(GuideFragment())
        fragments.add(GuideFragment())

        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {

            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }
        }
        xCircleIndicator.setUpLimit(2)
        xCircleIndicator.setDataCount(fragments.size)
        xCircleIndicator.setCurrentPage(0)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                xCircleIndicator.setCurrentPage(position)
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_circle_indicator_v2
    }
}