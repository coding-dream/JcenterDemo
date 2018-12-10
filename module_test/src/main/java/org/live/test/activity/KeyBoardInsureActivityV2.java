package org.live.test.activity;

import org.live.test.R;
import org.live.test.base.BaseActivity;
import org.live.test.util.WeakHandler;

/**
 * Created by wl on 2018/12/7.
 * 修复 OPPO 手机键盘弹起问题
 */
public class KeyBoardInsureActivityV2 extends BaseActivity {

    private WeakHandler weakHandler = new WeakHandler();

    @Override
    public void initView() {
        // 获取最外层的View容器

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_keyboard;
    }
}
