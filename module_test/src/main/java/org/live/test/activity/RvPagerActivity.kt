package org.live.test.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_rv_pager.*
import org.jetbrains.anko.toast
import org.live.test.R

/**
 * Created by wl on 2018/9/5.
 */
class RvPagerActivity : AppCompatActivity() {

    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_pager)
        adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.listitem_test) {

            override fun convert(helper: BaseViewHolder, item: String?) {

            }
        }
        recyclerViewPager.adapter = adapter
        recyclerViewPager.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewPager.setHasFixedSize(true)
        recyclerViewPager.isSinglePageFling = true
        recyclerViewPager.addOnPageChangedListener { oldPosition, newPosition ->
            if (oldPosition == newPosition) {
                // no change
                return@addOnPageChangedListener
            }
            toast("oldPosition: $oldPosition newPosition: $newPosition")
        }
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

