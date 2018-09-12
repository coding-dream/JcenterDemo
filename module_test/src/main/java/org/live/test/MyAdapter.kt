package org.live.test

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by wl on 2018/9/10.
 */
class MyAdapter : RecyclerView.Adapter<MyViewHolder>(){

    private var datas: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = View.inflate(parent.context, R.layout.item_test, null)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.setText("xxx")
    }

    fun setData(datas: List<String>) {
        this.datas.addAll(datas)
        notifyDataSetChanged()
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle: TextView = itemView.findViewById(R.id.tv_content)
}