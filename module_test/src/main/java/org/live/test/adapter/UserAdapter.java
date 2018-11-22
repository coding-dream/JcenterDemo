package org.live.test.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.live.test.R;
import org.live.test.bean.User;

import java.util.List;

/**
 * Created by wl on 2018/11/22.
 *
 * 参考: FansGroupListAdapter，可以把不同类型的实体作为User的成员变量。如 user.getVipData() user.getNormalData()
 */
public class UserAdapter extends BaseMultiItemQuickAdapter<User,BaseViewHolder> {

    public UserAdapter(List<User> data) {
        super(data);
        init();
    }

    private void init() {
        addItemType(User.TYPE_ITEM_ONE, R.layout.item_one);
        addItemType(User.TYPE_ITEM_NOMAL, R.layout.item_nomal);
        addItemType(User.TYPE_ITEM_VIP, R.layout.item_vip);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {
        switch (item.getItemType()){
            case User.TYPE_ITEM_ONE:
                convertOne(helper,item);
                break;
            case User.TYPE_ITEM_NOMAL:
                convertNormal(helper,item);
                break;
            case User.TYPE_ITEM_VIP:
                convertVip(helper,item);
                break;
            default:
                break;
        }

    }

    private void convertOne(BaseViewHolder helper, User item) {
        // 给子View添加点击事件
        helper.addOnClickListener(R.id.tv_name);
        helper.setText(R.id.tv_name, item.getName() + " one");
    }

    private void convertNormal(BaseViewHolder helper, User item) {
        helper.setText(R.id.tv_name, item.getName() + " normal");
    }

    private void convertVip(BaseViewHolder helper, User item) {
        helper.setText(R.id.tv_name, item.getName() + " vip");
    }
}
