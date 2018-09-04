package org.live.jtool.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lk on 2017/9/5.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected View mItemView;
    public ViewGroup mParentGroup;

    public BaseViewHolder(ViewGroup parentGroup, int layoutId) {
        this(parentGroup, LayoutInflater.from(parentGroup.getContext()).inflate(layoutId, parentGroup, false));
    }

    public BaseViewHolder(ViewGroup parentGroup, View itemView) {
        super(itemView);
        mItemView = itemView;
        mParentGroup = parentGroup;
        initViews(mItemView);
    }

    public abstract void initViews(View itemView);

    public abstract void updateViews(T data, int position);

    public View getItemView() {
        return mItemView;
    }
}