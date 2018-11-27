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
 *
 * 注意事项: RecyclerViewPager.RecyclerViewPagerAdapter.onBindViewHolder的实现根据水平或垂直ViewPager把每个Item的LayoutParams重新设置为LayoutParams.MATCH_PARENT
 * 所以RecyclerViewPager的宽高需要设置为MeasureSpec.EXACTLY, 否则measure失效.
 */
class RvPagerActivity : AppCompatActivity() {

    private lateinit var adapter: BaseQuickAdapter<String,BaseViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv_pager)
        adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.listitem_test) {

            override fun convert(helper: BaseViewHolder, item: String?) {
                helper.setText(R.id.tv_name, item)
            }
        }
        recyclerViewPager.adapter = adapter
        // 可以轻松实现抖音效果
        recyclerViewPager.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
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

