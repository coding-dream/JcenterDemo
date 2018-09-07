package org.live.baselib.shape

import android.content.Context
import android.content.res.ColorStateList
import android.support.annotation.ColorRes


/**
 * Created by wl on 2018/9/7.
 */

object TextColorHelper {
    /*SelectorState选中状态定义*/
    //是否按压状态
    val STATE_PRESSED = android.R.attr.state_pressed
    //触摸或点击事件是否可用状态
    val STATE_ENABLED = android.R.attr.state_enabled
    //是否选中状态
    val STATE_SELECTED = android.R.attr.state_selected
    //是否勾选状态 主要用于CheckBox和RadioButton
    val STATE_CHECKED = android.R.attr.state_checked
    //勾选是否可用状态
    val STATE_CHECKABLE = android.R.attr.state_checkable
    //是否获得焦点状态
    val STATE_FOCUSED = android.R.attr.state_focused
    //当前窗口是否获得焦点状态 例如拉下通知栏或弹出对话框时，当前界面就会失去焦点；
    val STATE_WINDOW_FOCUSED = android.R.attr.state_window_focused
    //是否被激活状态 API Level 11及以上才支持，可通过代码调用控件的setActivated(boolean)方法设置是否激活该控件
    val STATE_ACTIVATED = android.R.attr.state_activated
    //是否鼠标在上面滑动的状态 API Level 14及以上才支持
    val STATE_HOVERED = android.R.attr.state_hovered

    /**
     * 这里只演示常用的点击选中或未选中,其他类似
     */
    var mState: Int = STATE_PRESSED

    fun createColorStateList(context: Context, @ColorRes selectorColor: Int, @ColorRes nomalColor: Int): ColorStateList {
        val selectTextColor = context.getResources().getColor(selectorColor)
        val normalTextColor = context.getResources().getColor(nomalColor)
        val colors = intArrayOf(selectTextColor, normalTextColor)
        // nt[][] states = new int[2][];
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(mState)
        states[1] = intArrayOf(-mState)
        return ColorStateList(states, colors)
    }
}