package org.live.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.live.test.R;
import org.live.ui.expand_textview.ExpandableTextView;
import org.live.ui.expand_textview.TextState;

/**
 * Created by wl on 2018/11/22.
 *
 * 在 Activity 的 onCreate、onStart、OnResume 生命周期中，无法直接得到 View 的宽高信息。
 * 网上有以下几种常见的解决办法：
 * 1. 在 Activity#onWindowFocusChanged 回调中获取宽高。
 * 2. view.post(runnable)，在 runnable 中获取宽高。
 * 3. ViewTreeObserver 添加 OnGlobalLayoutListener，在 onGlobalLayout 回调中获取宽高。
 * 4. 调用 view.measure(0,0)，再通过 getMeasuredWidth 和 getMeasuredHeight 获取宽高。
 * 其中调用 view.measure(0,0)就相当于默认由ViewGroup {child.measure} 变成 程序员主动 view.measure
 * view.measure(0,0) 等价于 => measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
 *
 *
 * 针对最简单的 FrameLayout 和 ExpandableTextView 解析Measure的过程
 *
 * 有一部分看不太明白，当然也无需明白
 * 1. 首先是 FrameLayout.onMeasure方法.
 * 2. FrameLayout.measureChildWithMargins {
 *      childWidthMeasureSpec = getChildMeasureSpec(MeasureSpec.makeMeasureSpec(resultSize, resultMode);)
 *      childHeightMeasureSpec = getChildMeasureSpec(MeasureSpec.makeMeasureSpec(resultSize, resultMode);)
 *      child.measure()
 *      child.measure()
 *      child.onMeasure()
 *  }
 * 3. FrameLayout.setMeasuredDimension(我们发现ViewGroup的尺寸是在测量了child 后设置的)
 *
 * 其中getChildMeasureSpec的具体方法内容如下：
 * final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, mPaddingLeft + mPaddingRight + lp.leftMargin + lp.rightMargin + widthUsed, lp.width);
 * final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec, mPaddingTop + mPaddingBottom + lp.topMargin + lp.bottomMargin + heightUsed, lp.height);
 *
 * ## child.onMeasure
 * 该方法一般都被子View重写，可以参考TextView或者ImageView的onMeasure方法:
 *
 * 参考文章: [调用view.measure(0,0)时发生了什么](https://www.jianshu.com/p/dbd6afb2c890)
 *
 * @Override
 * protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
 *     int w; //宽
 *     int h; //高
 *
 *     w = mDrawableWidth;
 *     h = mDrawableHeight;
 *
 *     int widthSize;
 *     int heightSize;
 *
 *     w = Math.max(w, getSuggestedMinimumWidth());
 *     h = Math.max(h, getSuggestedMinimumHeight());
 *
 *     widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
 *     heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);
 *
 *     setMeasuredDimension(widthSize, heightSize);
 * }
 * 重点原因在: resolveSizeAndState内。
 *
 *
 * AT_MOST,EXACTLY,UNSPECIFIED
 * UNSPECIFIED
 * 父容器不会对子元素加以任何约束，子元素可以是任何大小。
 *
 */
public class MeasureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);


        TextState textState = new TextState();
        textState.mTextState = TextState.STATE_COLLAPSED;

        // 注意此部分onCreate中ExpandableTextView还没有被绘制完成，所以得到的ExpandableTextView.getMeasuredWidth可能还是0
        ExpandableTextView expandableTextView = findViewById(R.id.expandableTextView);
        View vMore = findViewById(R.id.v_more);
        expandableTextView.setText(getResources().getString(R.string.content_long));
        expandableTextView.setMaxLines(10);
        expandableTextView.toggle(textState, vMore);
    }
}