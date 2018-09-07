package org.live.baselib

import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * Created by wl on 2018/9/5.
 *
 * 扩展函数
 */
fun AppCompatActivity.toast(str: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, str, duration).show()
}

object Helper{

}