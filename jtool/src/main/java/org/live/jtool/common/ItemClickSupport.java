package org.live.jtool.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * BaseRecycleViewAdapter 点击长按事件处理
 */
public class ItemClickSupport {

    public interface OnItemClickListener {
        void onItemClick(RecyclerView recyclerView, View view, int position, long id);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(RecyclerView recyclerView, View view, int position, long id);
    }

    private RecyclerView mRecyclerView;
    private BaseRecycleViewAdapter mAdapter;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public ItemClickSupport(RecyclerView recyclerView, BaseRecycleViewAdapter adapter) {
        this.mRecyclerView = recyclerView;
        this.mAdapter = adapter;
        this.mAdapter.setItemClickSupport(this);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        if (null != onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        if (null != onItemLongClickListener) {
            this.mOnItemLongClickListener = onItemLongClickListener;
        }
    }

    public void onItemClick(View v) {
        if (null != mRecyclerView && null != mAdapter && null != mOnItemClickListener) {
            final int position = mRecyclerView.getChildLayoutPosition(v);
            final long id = mAdapter.getItemId(position);
            mOnItemClickListener.onItemClick(mRecyclerView, v, position, id);
        }
    }

    public boolean onItemLongClick(View v) {
        if (null != mRecyclerView && null != mAdapter && null != mOnItemLongClickListener) {
            final int position = mRecyclerView.getChildLayoutPosition(v);
            final long id = mAdapter.getItemId(position);

            return mOnItemLongClickListener.onItemLongClick(mRecyclerView, v, position, id);
        }

        return false;
    }
}