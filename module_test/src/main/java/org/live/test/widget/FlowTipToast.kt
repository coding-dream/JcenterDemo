package org.live.test.widget

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import org.live.test.R

class FlowTipToast {

    companion object {

        fun show(context: Context, text1: String, text2: String, duration: Int = Toast.LENGTH_LONG) {
            val toast = Toast(context)
            val view = LayoutInflater.from(context).inflate(R.layout.zz_layout_flowtip_dialog, null)
            view.findViewById<TextView>(R.id.tv_tip1).text = text1
            view.findViewById<TextView>(R.id.tv_tip2).text = text2
            toast.view = view
            toast.duration = duration
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }
}