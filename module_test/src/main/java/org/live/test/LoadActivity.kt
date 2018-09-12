package org.live.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_lode_more.*

/**
 * Created by wl on 2018/9/5.
 */
class LoadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lode_more)

        recycleView.adapter = MyAdapter()
        recycleView.layoutManager = LinearLayoutManager(this)
        var datas = ArrayList<String>()
        for (i in 1..100) {
            datas.add(i.toString())
        }
        var myAdapter = recycleView.adapter as MyAdapter
        myAdapter.setData(datas)
    }
}