package org.live.ui
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by wl on 2018/10/7.
 */
class CustomView(context: Context, attributeSet: AttributeSet? = null) : View(context, attributeSet) {

    private var mPaint: Paint

    private var dstBmp: Bitmap

    private var srcBmp: Bitmap

    private val mWidth = 400

    private val mHeight = 400

    init {
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        dstBmp = makeDst(mWidth , mHeight)
        srcBmp = makeSrc(mWidth , mHeight)
    }

    /**
     * 目标图像(圆形)
     */
    private fun makeDst(width: Int, height: Int): Bitmap {
        val bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = Color.RED
        c.drawOval(RectF(0f, 0f, width.toFloat(), height.toFloat()), p)
        return bm
    }

    /**
     * 源图像(矩形)
     */
    private fun makeSrc(width: Int, height: Int): Bitmap {
        val bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(bm)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        // 设置了一定的透明度,容易看出区别
        p.color = Color.BLUE
        c.drawRect(0F, 0F, width.toFloat(), height.toFloat(), p)
        return bm
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val layerID = canvas.saveLayer(0F, 0F, (width * 2).toFloat(), (height * 2).toFloat(), mPaint, Canvas.ALL_SAVE_FLAG)
        canvas.drawBitmap(dstBmp, 0F, 0F, mPaint)
        // 源图像模式: [Sa * Da, Sc * Da] 源图像的像素计算 -> 由dst(目标图像)的 Da透明度来控制
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(srcBmp, (mWidth / 2).toFloat(), (mHeight / 2).toFloat(), mPaint)
        mPaint.xfermode = null
        canvas.restoreToCount(layerID)
    }
}