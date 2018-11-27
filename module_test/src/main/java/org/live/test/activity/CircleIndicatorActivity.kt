package org.live.test.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_circle_indicator.*
import org.live.test.R
import org.live.test.base.BaseActivity
import java.util.*

/**
 * Created by wl on 2018/11/26.
 *
 * 参考: PicturePreviewActivity
 */
class CircleIndicatorActivity : BaseActivity() {

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

        circleIndicator.setViewPager(viewPager, false)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_circle_indicator
    }
}

class GuideFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_guide, container, false)
        initView(rootView)
        return rootView
    }

    private fun initView(rootView: View) {
        val tvContent = rootView.findViewById<TextView>(R.id.tv_content)
        val num = Random().nextInt(10)
        tvContent.text = "info: $num"
    }
}