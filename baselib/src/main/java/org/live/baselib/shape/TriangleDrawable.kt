package org.live.baselib.shape

import android.graphics.*
import android.graphics.drawable.Drawable

/**
 * Created by wl on 2018/10/18.
 */
class TriangleDrawable(val color: Int = Color.RED) : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    init {
        paint.color = color
        paint.style = Paint.Style.FILL
        // paint.style = Paint.Style.STROKE
    }

    /**
     * 在View上绘制背景 -> 下三角
     */
    override fun draw(canvas: Canvas) {
        // 实例化路径
        val path = Path()
        path.moveTo(80f, 200f)
        path.lineTo(120f, 250f)
        path.lineTo(80f, 250f)
        path.close()
        canvas.drawPath(path, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
        invalidateSelf()
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
        invalidateSelf()
    }
}