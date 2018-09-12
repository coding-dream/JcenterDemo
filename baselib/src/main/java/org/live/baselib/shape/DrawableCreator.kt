package org.live.baselib.shape

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v7.widget.DrawableUtils
import android.view.View


/**
 * Created by wl on 2018/9/7.
 */

class DrawableCreator {
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

    fun createSelectorDrawable(selectDrawable: Drawable, nomalDrawable: Drawable): StateListDrawable {
        val stateListDrawable = StateListDrawable()
        stateListDrawable.addState(intArrayOf(mState), selectDrawable)// 状态为true的背景
        stateListDrawable.addState(intArrayOf(-mState), nomalDrawable)// 状态为false的背景
        return stateListDrawable
    }

    fun createColorDrawable(context: Context, @ColorRes colorId: Int): ColorDrawable {
        return ColorDrawable(context.resources.getColor(colorId))
    }

    /**
     * 从xml中加载Drawable对象,然后再修改对应属性
     */
    fun createDefaultDrawable(context: Context, @DrawableRes id: Int): Drawable? {
        return context.getResources().getDrawable(id)
    }
}

/**
 * 设置View的背景 Drawable类型
 */
fun View.into(bgDrawable: Drawable) {
    background = bgDrawable
}

fun dip2px(context: Context, dipValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}
