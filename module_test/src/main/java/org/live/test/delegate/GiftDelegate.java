package org.live.test.delegate;

import android.os.Handler;
import android.view.View;
import android.view.ViewStub;

import org.live.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wl on 2018/11/26.
 */
public class GiftDelegate {

    private ViewStub viewStub;
    private Unbinder unbinder;

    /**
     * 注意: rootView和layoutRoot是同一个View.
     */
    private View rootView;
    @BindView(R.id.layout_root)
    View layoutRoot;

    public void onCreate(ViewStub layoutViewStub) {
        this.viewStub = layoutViewStub;
        initView();
    }

    private void initView() {
        rootView = viewStub.inflate();
        unbinder = ButterKnife.bind(this, rootView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 下面两个其实是等价的.
                rootView.setVisibility(View.VISIBLE);
                layoutRoot.setVisibility(View.VISIBLE);
            }
        },5000);
    }

    public void onDestroy() {
        if (rootView != null && unbinder != null) {
            unbinder.unbind();
        }
        this.rootView = null;
        this.viewStub = null;
    }

    @OnClick({R.id.btnShow,R.id.btnHide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnShow:
                layoutRoot.setVisibility(View.VISIBLE);
                break;
            case R.id.btnHide:
                layoutRoot.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
}