package org.live.ui

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.android.synthetic.main.cv_layout_bottom.view.*

/**
 * Created by wl on 2018/11/28.
 */
class BottomMenuLayout(context: Context, attributeSet: AttributeSet? = null) : LinearLayout(context, attributeSet) {

    private val menuAdapter by lazy { MenuAdapter() }

    init {
        initViews()
    }

    fun initViews() {
        inflate(context, R.layout.cv_layout_bottom, this)
    }

    fun setDatas(menuDatas: MutableList<MenuItem>, menuCount: Int) {
        recyclerView.layoutManager = GridLayoutManager(context, menuCount) as RecyclerView.LayoutManager
        recyclerView.adapter = menuAdapter
        menuAdapter.setNewData(menuDatas)

        // 默认选中第一个item
        menuAdapter.chooseMe(0)
    }
}

class MenuAdapter : BaseQuickAdapter<MenuItem, BaseViewHolder>(R.layout.zz_menu_list_item) {

    /** 单选 */
    var currentPosition = -1

    override fun convert(helper: BaseViewHolder, item: MenuItem) {
        helper.setText(R.id.tv_menu_name, item.name)

        helper.getView<View>(R.id.layout_item).isSelected = currentPosition == helper.adapterPosition

        if (currentPosition == helper.adapterPosition) {
            doAnimator(helper.itemView)
        }

        helper.itemView.setOnClickListener {
            val position = helper.adapterPosition
            chooseMe(position)
        }
    }

    private fun doAnimator(itemView: View) {
        val textAnimator = makeTabAnimator(itemView)
        textAnimator.interpolator = LinearInterpolator()
        textAnimator.duration = 250
        textAnimator.start()
    }

    private fun makeTabAnimator(itemView: View): ObjectAnimator {
        val kf0 = Keyframe.ofFloat(0.0f, 1.0f)
        val kf01 = Keyframe.ofFloat(0.4f, 1.15f)
        val kf11 = Keyframe.ofFloat(0.7f, 0.9f)
        val kf2 = Keyframe.ofFloat(1.0f, 1.0f)
        val pvhX = PropertyValuesHolder.ofKeyframe(View.SCALE_X, kf0, kf01, kf11, kf2)
        val pvhY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y, kf0, kf01, kf11, kf2)
        return ObjectAnimator.ofPropertyValuesHolder(itemView, pvhX, pvhY)
    }

    /**
     * notifyItemChanged(position, 任意对象)
     * 注意: 修复局部刷新的坑(更新单个item, 整个item闪动的问题,尤其是图片)
     * 参考: https://www.jianshu.com/p/57e569087ffc
     */
    fun chooseMe(position: Int) {
        if (currentPosition == position) {
            return
        } else if (currentPosition != position && currentPosition != -1) {
            // 先取消上一个item的状态
            notifyItemChanged(currentPosition, 666)
            currentPosition = position
            // 设置新item的状态
            notifyItemChanged(position, 666)
        } else if(currentPosition == -1) {
            // 不需要取消上一个item状态
            // 设置的item的状态
            currentPosition = position
            notifyItemChanged(position, 666)
        }
    }
}

data class MenuItem(var name: String)