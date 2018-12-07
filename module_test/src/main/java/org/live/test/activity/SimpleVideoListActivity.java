package org.live.test.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.live.player.littlevideo.LittleVideoActivity;
import org.live.test.R;
import org.live.test.base.BaseActivity;
import org.live.test.test.MockHelper;
import org.live.ui.MenuItem;

import butterknife.BindView;

/**
 * Created by wl on 2018/12/6.
 */
public class SimpleVideoListActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        BaseQuickAdapter baseQuickAdapter = new BaseQuickAdapter<MenuItem, BaseViewHolder>(R.layout.listitem_defalut_img_txt, MockHelper.getMenuDatas()) {
            @Override
            protected void convert(BaseViewHolder helper, MenuItem item) {
                helper.setText(R.id.tv_txt, item.getName());
            }
        };
        recyclerView.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(SimpleVideoListActivity.this, LittleVideoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_default_recycle_view;
    }
}
