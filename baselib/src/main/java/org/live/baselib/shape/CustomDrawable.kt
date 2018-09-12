package org.live.baselib.shape

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

/**
 * Created by wl on 2018/9/12.
 *
 * 参考:
 * 1. android.support.v4.graphics.drawable.RoundedBitmapDrawable
 * 2. android.support.v7.widget.DrawableUtils
 * https://blog.csdn.net/yanbober/article/details/56844869
 */
class CustomDrawable(val color: Int) : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.color = color
    }

    /**
     * 在View上绘制背景 -> 圆形
     */
    override fun draw(canvas: Canvas) {
        // bounds参数: Drawale的实际区域大小,一般设置成与View大小相同,一般是我们在CustomView中的onMeasure中设置的,如: CustomView -> mDrawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
        // 而getIntrinsicHeight和getIntrinsicWidth表示Drawable内部画的内容大小(ColorDrawable无内容大小) 如: image, 但不管是什么大小(无需记忆),真正控制显示的是我们的draw方法,什么大小都不重要,甚至我们自定义宽高属性都行.
        val rect = bounds
        val cx = rect.exactCenterX()
        val cy = rect.exactCenterY()
        canvas.drawCircle(cx, cy, Math.min(cx, cy), paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
        invalidateSelf()
    }

    /**
     *  返回当前Drawable透明或者半透明或者不透明等，默认不清楚时直接返回TRANSLUCENT是最好的选择
     * {@link android.graphics.PixelFormat}:
     * {@link android.graphics.PixelFormat#UNKNOWN},
     * {@link android.graphics.PixelFormat#TRANSLUCENT},
     * {@link android.graphics.PixelFormat#TRANSPARENT} or {@link android.graphics.PixelFormat#OPAQUE}
     */
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
        invalidateSelf()
    }

}