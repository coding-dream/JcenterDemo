package org.live.test.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.view.Gravity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_rv_pager_v2.*
import org.live.baselib.toast
import org.live.test.R
import org.live.ui.rvpager_v2.GravityPagerSnapHelper
import org.live.ui.rvpager_v2.GravitySnapHelper


/**
 * Created by wl on 2018/9/5.
 * 滑动页的监听器尽量不要用此库，存在Bug. 用RecyclerViewPager 代替
 *
 * Bugs:
 * 假设 SnapHelper的ScrollListener为 s1. GravityDelegate的ScrollListener为 s2
 * 1. 当滑动不到一页的时候，状态会回调多次 1(s1),1(s2),0(s1),2(s1),2(s2),0(s2),0(s1),0(s2), 可以很明显发现如果用户没有划过一页， s1和s2都会多余一次状态0的回调。
 * 2. 如果用户迅速滑到第一页(滑倒第一个又迅速滑一次), 状态只会回调 1(s1), 1(s2)。 这里可能性比较大的是第一次状态(就像动画一样)还没有回调，就被第二次的滑动导致取消了。
 */
class RvPagerActivityV2 : AppCompatActivity() {

    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_pager_v2)
        adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.listitem_test) {

            override fun convert(helper: BaseViewHolder, item: String?) {
                helper.setText(R.id.tv_name, item)
            }
        }
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycleView.setHasFixedSize(true)

        val gravitySnapHelper = GravitySnapHelper(Gravity.START, true, GravitySnapHelper.SnapListener { position ->
            toast("Snapped $position")
        })

        // 目前有个小bug就是: GravityPagerSnapHelper(Gravity.START...) 快速回到第一个item,监听器未回调.
        val gravityPageSnapHelper = GravityPagerSnapHelper(Gravity.START, true, GravitySnapHelper.SnapListener { position ->
            toast("PageSnapped $position")
        })

        val pageSnapHelper = PagerSnapHelper()
        gravityPageSnapHelper.attachToRecyclerView(recycleView)
        initData()
    }

    private fun initData() {
        val list = arrayListOf<String>()
        for (i in 0..5) {
            list.add("$i")
        }
        adapter.setNewData(list)
    }
}