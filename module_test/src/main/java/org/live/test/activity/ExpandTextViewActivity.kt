package org.live.test.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewTreeObserver
import kotlinx.android.synthetic.main.activity_expand_textview.*
import org.live.test.R
import org.live.ui.expand_textview.ExpandableTextView
import org.live.ui.expand_textview.TextState

/**
 * Created by wl on 2018/11/21.
 */
class ExpandTextViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expand_textview)

        tv_text.text = resources.getString(R.string.content_long)

        val textState = TextState()
        textState.mTextState = TextState.STATE_UNKNOWN
        // 初始化状态
        checkState(textState)

        btn_toggle.setOnClickListener {
            tv_text.toggle(textState, tv_read_more)
        }
    }

    private fun checkState(textState: TextState) {
        if (textState.mTextState == TextState.STATE_UNKNOWN) {
            tv_text.maxLines = Integer.MAX_VALUE
            tv_text.maxLines = Integer.MAX_VALUE
            tv_text.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // 这个回调会调用多次，获取完行数记得注销监听
                    tv_text.viewTreeObserver.removeOnPreDrawListener(this)

                    // 如果文本行数 > 大于折叠的限定行数
                    if (tv_text.lineCount > ExpandableTextView.MAX_LINE_COUNT) {
                        tv_text.maxLines = ExpandableTextView.MAX_LINE_COUNT
                        textState.mTextState = TextState.STATE_COLLAPSED
                    } else {
                        tv_text.maxLines = Int.MAX_VALUE
                        textState.mTextState = TextState.STATE_EXPANDED
                    }
                    return true
                }
            })
        }
    }
}