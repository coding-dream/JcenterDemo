package org.live.test.activity

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import org.live.test.R

/**
 * Created by wl on 2018/10/10.
 */
class StaggeredGridActivity : AppCompatActivity() {

    val myAdapter = MyAdapter()
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycleView.adapter = myAdapter
        recycleView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        swipeRefreshLayout.setOnRefreshListener {
            handler.postDelayed({
                val datas = fetchData()
                myAdapter.setNewData(datas)
                swipeRefreshLayout.isRefreshing = false
            }, 500)
        }
        myAdapter.setOnLoadMoreListener({
            loadMore()
        }, recycleView)
        initData()
    }

    private fun initData() {
        myAdapter.setNewData(fetchData())
    }

    private fun loadMore() {
        handler.postDelayed({
            val datas = fetchData()
            myAdapter.addData(datas)
            myAdapter.loadMoreComplete()
        },100)
    }

    private fun fetchData(): ArrayList<String> {
        val list = ArrayList<String>()
        for (i in 0..20) {
            list.add("value: $i")
        }
        return list
    }
}

class MyAdapter(val datas: List<String> = ArrayList())
    : BaseQuickAdapter<String, BaseViewHolder>(R.layout.nn_first_letter_list_item, datas) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.addOnClickListener(R.id.tv_name)
        helper.setText(R.id.tv_name, item)
        helper.setText(R.id.tv_position, "layoutPosition: ${helper.layoutPosition}")
    }
}