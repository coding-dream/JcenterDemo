package org.live.test.activity;

import org.jetbrains.anko.ToastsKt;
import org.live.test.App;
import org.live.test.R;
import org.live.test.base.BaseActivity;

/**
 * Created by wl on 2018/11/30.
 */
public class AppGlobalActivity extends BaseActivity {

    @Override
    public void initView() {
        App.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastsKt.toast(AppGlobalActivity.this, "toast");
            }
        }, 3000);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_default;
    }
}
