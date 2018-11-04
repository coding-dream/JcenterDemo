package org.live.test.activity

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_viewpager.*
import org.live.baselib.util.SystemUtils
import org.live.test.R
import android.util.SparseArray
import org.live.ui.ZoomOutPageTransformer


/**
 * Created by wl on 2018/9/5.
 */
class ViewPagerActivity : AppCompatActivity() {

    private lateinit var adapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        val datas = arrayListOf<String>()
        for (i in 0..5) {
            datas.add("$i")
        }

        adapter = object : PagerAdapter() {

            private val sparseArray: SparseArray<View> = SparseArray(10)

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                var cacheView = sparseArray.get(position)
                if (cacheView == null) {
                    cacheView = LayoutInflater.from(container.context).inflate(R.layout.listitem_test, container, false)
                    sparseArray.put(position, cacheView)
                    if (position == 0) {
                        animateFirstView(cacheView)
                    }
                }
                container.addView(cacheView)
                return cacheView
            }

            private fun animateFirstView(view: View) {
                view.scaleX = 0.5f
                view.scaleY = 0.5f
                view.alpha = 0.5f
                view.animate()
                        .alpha(1f)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(1000)
                        .start()
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

            /**
             * 默认是1, 需求：当前展示的页面右侧显示一部分下个页面的内容
             * 参考: https://blog.csdn.net/JM_beizi/article/details/51297605
             */
            override fun getPageWidth(position: Int): Float {
                if (position == datas.size - 1) {
                    return 1.0f
                }
                return 0.8f
            }

            /**
             * ViewPager不刷新的解决方式
             * link: https://www.jianshu.com/p/266861496508
             */
            override fun getItemPosition(`object`: Any): Int {
                return POSITION_NONE
            }
        }

        viewPager.adapter = adapter
        viewPager.pageMargin = SystemUtils.dip2px(this, 16f)
        val pageListener = object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                Logger.d("onPageScrolled: position: $position positionOffset: $positionOffset")
            }
        }
        viewPager.addOnPageChangeListener(pageListener)
        // viewPager.setPageTransformer(false, ZoomOutPageTransformer())
        myIndicator.attachToViewPager(viewPager)
    }
}