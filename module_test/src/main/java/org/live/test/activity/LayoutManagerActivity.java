package org.live.test.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.live.test.R;
import org.live.test.base.BaseActivity;
import org.live.test.test.MockHelper;
import org.live.ui.MenuItem;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wl on 2018/11/30.
 *
 * todo 自定义RecycleView的LayoutManager(重量级控件)
 */
public class LayoutManagerActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void initView() {
        List<MenuItem> datas = MockHelper.getMenuDatas();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new BaseQuickAdapter<MenuItem, BaseViewHolder>(R.layout.listitem_layout_manager, datas) {
            @Override
            protected void convert(BaseViewHolder helper, MenuItem item) {
                helper.setText(R.id.tv_content, item.getName());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_default_recycle_view;
    }
}
