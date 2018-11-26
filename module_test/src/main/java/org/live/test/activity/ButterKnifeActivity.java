package org.live.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;

import org.live.test.R;
import org.live.test.delegate.GiftDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wl on 2018/11/2.
 *
 * ButterKnife 这个库在本项目中无法使用,fuck,不知道是否和那个库冲突了, 下面的代码仅供参考.
 */
public class ButterKnifeActivity extends AppCompatActivity {

    @BindView(R.id.layout_view_stub)
    ViewStub layoutViewStub;

    @BindView(R.id.tv_content)
    View vContent;

    private GiftDelegate giftDelegate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_butterknife);
        ButterKnife.bind(this);

        giftDelegate = new GiftDelegate();
        giftDelegate.onCreate(layoutViewStub);
    }
}