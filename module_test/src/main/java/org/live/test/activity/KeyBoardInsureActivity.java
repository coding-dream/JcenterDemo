package org.live.test.activity;

import android.view.View;
import android.widget.FrameLayout;

import org.live.test.R;
import org.live.test.base.BaseActivity;
import org.live.test.util.LogHelper;
import org.live.test.util.WeakHandler;

/**
 * Created by wl on 2018/12/7.
 * 修复 OPPO 手机键盘弹起问题
 */
public class KeyBoardInsureActivity extends BaseActivity {

    private WeakHandler weakHandler = new WeakHandler();

    @Override
    public void initView() {
        // 获取最外层的View容器
        FrameLayout contentView = findViewById(android.R.id.content);
        View childOfContent = contentView.getChildAt(0);
        FrameLayout.LayoutParams frameLayoutParams = (FrameLayout.LayoutParams) childOfContent.getLayoutParams();
        frameLayoutParams.height = 1920;
        resizeRootLayout(childOfContent, frameLayoutParams);
    }

    private void resizeRootLayout(View childOfContent, FrameLayout.LayoutParams frameLayoutParams) {
        LogHelper.d("resizeRootLayout: " + frameLayoutParams.height);

        frameLayoutParams.height -= 0;
        // frameLayoutParams.height -= 20;
        // 如果frameLayoutParam.height不变化, ResizeLayout.onSizeChange() 不会调用, 只有size变化才会回调.
        // 而且键盘的顶起不会导致布局大小变化和onSizeChange也不会改变, 变化是由于我们主动设置LayoutParams导致的.
        childOfContent.requestLayout();

        weakHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resizeRootLayout(childOfContent, frameLayoutParams);
            }
        }, 1000);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_keyboard;
    }
}
