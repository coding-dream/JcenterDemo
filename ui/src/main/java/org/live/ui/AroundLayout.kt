package org.live.ui

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout

/**
 * Created by wl on 2018/11/29.
 *
 * todo 暂时待定
 */
class AroundLayout(context: Context, attributeSet: AttributeSet? = null) : ConstraintLayout(context, attributeSet) {

    init {
        initView()
    }

    private lateinit var layoutMain: View
    private lateinit var layoutLeft: View
    private lateinit var layoutRight: View


    private val INVALID_ID = -1
    private var mFirstPointerId = INVALID_ID
    private var mSecondPointerId = INVALID_ID

    private var mFirstLastX: Float = 0f
    private var mFirstLastY: Float = 0f

    private var mSecondLastX: Float = 0f
    private var mSecondLastY: Float = 0f

    private var offsetX: Float = 0f
    private var offsetY: Float = 0f

    private fun initView() {
        LinearLayout.inflate(context, R.layout.zz_layout_around, this)
        layoutMain = findViewById<View>(R.id.layout_main)
        layoutLeft = findViewById<View>(R.id.layout_left)
        layoutRight = findViewById<View>(R.id.layout_right)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val tempIndex: Int
        val action = event.actionMasked
        // 多触摸时间很多案例调用的是这个方法,但是官方提示该类已过时推荐直接用event.getAction(),但是event.getAction() 导致预想结果不一致.
        // PointerId 下标是从0开始递增的,即第一个触摸点PointerId = 0, 第二个触摸点 PointerId = 1
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                // ACTION_DOWN 只在第一个手指按下时候触发,所以event.getPointerId(index)一定是0.
                // ACTION_MOVE 可以触发两个手指事件,三个则报: ViewRootImpl: cancle motionEvent because of threeGesture detecting 异常,不清楚原因.

                // 测试的结果:
                // 1. ACTION_DOWN 先执行(代表的一定是第一个pointer)
                // 2. ACTION_POINTER_DOWN:代表用户又使用一个手指触摸到屏幕上，也就是说，在已经有一个触摸点的情况下，有新出现了一个触摸点
                // 3. ACTION_POINTER_UP 在多个触摸点存在的情况下，其中一个触摸点消失了.此时该方法内部还是event.getPointerCount() 还未减去该移除的触摸点.
                // 4. ACTION_MOVE 中 MotionEvent封装了多个触摸点事件,可以根据需求获取每个pointer的坐标x,y .
                tempIndex = event.actionIndex
                // event.getX() <==> event.getX(0);
                // event.getY() <==> event.getY(0);
                // 这里直接用event.getX()即可,仅为演示例子
                mFirstLastX = event.getX(tempIndex)
                mFirstLastY = event.getY(tempIndex)

                // 保存触摸点的id(id值是唯一的,为了在ACTION_MOVE中获取这个触摸点的 x,y)
                mFirstPointerId = event.getPointerId(tempIndex)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {

                tempIndex = event.actionIndex
                mSecondLastX = event.getX(tempIndex)
                mSecondLastY = event.getY(tempIndex)

                // 保存触摸点的id(id值是唯一的,为了在ACTION_MOVE中获取这个触摸点的 x,y)
                mSecondPointerId = event.getPointerId(tempIndex)
            }
            MotionEvent.ACTION_MOVE -> {

                // 为了效率,Android 会把【多个触摸点事件】打包放在一个MotionEvent对象中(所以不能像在ACTION_DOWN 和 ACTION_POINTER_DOWN中那样获取pointer的tempIndex = event.getActionIndex())
                tempIndex = event.findPointerIndex(mFirstPointerId)
                val cx = event.getX(tempIndex).toInt()
                val cy = event.getY(tempIndex).toInt()

                offsetX = cx - mFirstLastX
                offsetY = cy - mFirstLastX

                mFirstLastX = cx.toFloat()
                mFirstLastY = cx.toFloat()

                updateLayout()
            }
            MotionEvent.ACTION_POINTER_UP -> {
                // 每次从屏幕中移除一个触摸点时候触发(前提是屏幕至少存在一个触摸点)
                tempIndex = event.actionIndex
                val remotePointerId = event.getPointerId(tempIndex)
                // 如果主导滑动的第一个触摸点被移除了,那么就按照第二个触摸点滑动得了.
                if (remotePointerId == mFirstPointerId) {
                    mFirstPointerId = mSecondPointerId
                }
            }
            MotionEvent.ACTION_UP -> {
                // 屏幕中移除最后一个触摸点时候触发
            }
            MotionEvent.ACTION_CANCEL -> {
            }
            else -> {
            }
        }
        return true
    }

    private fun updateLayout() {
        layoutMain.offsetLeftAndRight(offsetX.toInt() / 2)
    }
}