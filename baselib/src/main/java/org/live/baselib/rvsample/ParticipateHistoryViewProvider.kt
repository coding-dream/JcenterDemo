package org.live.baselib.rvsample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nono.android.modules.setting.luckdraw.bean.ParticipateHistory
import me.drakeet.multitype.ItemViewBinder
import org.live.baselib.R

class ParticipateHistoryViewProvider : ItemViewBinder<ParticipateHistory, ParticipateHistoryViewProvider.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val rootView = inflater.inflate(R.layout.nn_luckdraw_participate_list_item, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, participateHistory: ParticipateHistory) {
        holder.tvLoginName.setText(participateHistory.loginname?:"")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvLoginName: TextView = itemView.findViewById(R.id.tv_loginname)
    }
}