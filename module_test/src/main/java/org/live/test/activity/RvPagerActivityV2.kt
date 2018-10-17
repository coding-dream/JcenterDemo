package org.live.test.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
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
 */
class RvPagerActivityV2 : AppCompatActivity() {

    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_pager_v2)
        adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.listitem_test) {

            override fun convert(helper: BaseViewHolder, item: String?) {

            }
        }
        recycleView.adapter = adapter
        recycleView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycleView.setHasFixedSize(true)

        // 目前有个小bug就是: GravityPagerSnapHelper(Gravity.START...) 快速回到第一个item,监听器未回调.
        val gravitySnapHelper = GravityPagerSnapHelper(Gravity.START, true, GravitySnapHelper.SnapListener { position ->
            toast("Snapped $position")
        })

        gravitySnapHelper.attachToRecyclerView(recycleView)
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