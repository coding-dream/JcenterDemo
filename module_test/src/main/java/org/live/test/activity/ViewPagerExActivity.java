package org.live.test.activity;

import org.live.test.R;
import org.live.test.base.BaseActivity;
import org.live.ui.ViewPagerEx;

import butterknife.BindView;

/**
 * Created by wl on 2018/12/6.
 */
public class ViewPagerExActivity extends BaseActivity {

    @BindView(R.id.viewPagerEx)
    ViewPagerEx viewPagerEx;

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_viewpager_ex;
    }
}
