package org.live.jtool.common;

import android.content.pm.PackageManager.NameNotFoundException;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * /**
 * Created by lk on 16/4/18.
 * RecycleView adapter 基类
 * @param <T>
 */
@SuppressWarnings({"WeakerAccess", "unchecked", "unused"})
public abstract class BaseRecycleViewAdapter<T extends ICommonAdapterBean > extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mData;

    /**
     * 绑定itemlayout
     *
     * @param parent
     * @return
     */
    protected abstract RecyclerView.ViewHolder createVHolder(ViewGroup parent, int viewType);

    /**
     * 设置数据，展现内容
     *
     * @param holder
     * @param position
     */
    protected void showItemView(RecyclerView.ViewHolder holder, int position) throws NameNotFoundException {
        if (holder instanceof BaseViewHolder) {
            ((BaseViewHolder) holder).updateViews(getItemData(position), position);
        }
    }

    public long getAdapterItemId(int position) {
        return position;
    }

    private ItemClickSupport mItemClickSupport;

    private ErrorViewHolder.OnReloadClickListener mReloadClickListener;


    public int getAdapterItemCount() {
        return null != mData ? mData.size() : 0;
    }

    protected int obtainItemViewType(int position) {
        return getItemData(position).getViewType();
    }


    @Override
    public long getItemId(int position) {
        return  getAdapterItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return obtainItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return getAdapterItemCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == ICommonAdapterBean.ViewType.VIEW_TYPE_ERROR) {
            viewHolder = new ErrorViewHolder(parent, mReloadClickListener);
        } else {
            viewHolder = createVHolder(parent, viewType);
        }
        if (mItemClickSupport != null) {
            viewHolder.itemView.setOnClickListener(mClickListener);
            viewHolder.itemView.setOnLongClickListener(mLongClickListener);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            showItemView(holder, position);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setData(@NonNull List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        if (mData == null) {
            return Collections.emptyList();
        }
        return mData;
    }

    public T getItemData(int position) {
        return ArrayUtils.safeGet(mData, position);
    }

    public boolean contains(T data) {
        return mData != null && mData.contains(data);
    }

    public void addDataEnd(T data) {
        if (data != null && null != mData) {
            int startIndex = this.mData.size();
            this.mData.add(data);
            filterData(this.mData);
            notifyItemInserted(startIndex);
        }
    }

    public void addData(T data, int index) {
        if (null != data && null != mData && 0 <= index && getAdapterItemCount() >= index) {
            this.mData.add(index, data);
            filterData(mData);
            notifyItemInserted(index);
        }
    }

    public void addData(List<T> data, int index) {
        if (null != data && null != mData && 0 <= index && getAdapterItemCount() >= index) {
            this.mData.addAll(index, data);
            filterData(mData);
            notifyItemRangeInserted(index, data.size());
        }
    }

    public void addDataEnd(List<T> data) {
        if (data != null && null != mData && data.size() > 0 && data != this.mData) {
            int startIndex = this.mData.size();
            this.mData.addAll(data);
            filterData(this.mData);
            notifyItemRangeInserted(startIndex, data.size());
        }
    }

    public void addDataTop(List<T> data) {
        if (null != data && null != mData && 0 < data.size() && data != this.mData) {
            this.mData.addAll(0, data);
            filterData(mData);
            notifyItemRangeInserted(0, data.size());
        }
    }

    public void addDataTop(T data) {
        if (null != data && null != mData) {
            this.mData.add(0, data);
            filterData(mData);
            notifyItemInserted(0);
        }
    }

    public void remove(T data) {
        if (data != null && null != mData) {
            if (mData.contains(data)) {
                int startIndex = this.mData.indexOf(data);
                mData.remove(data);
                if (startIndex != -1) {
                    filterData(this.mData);
                    notifyItemRemoved(startIndex);
                }

            }
        }
    }

    public void remove(int index) {
        if (null != mData && 0 <= index && getAdapterItemCount() >= index) {
            mData.remove(index);
            filterData(mData);
            notifyItemRemoved(index);
        }
    }

    public void removeRange(int index, int itemCount) {
        if (null != mData && 0 <= index && getAdapterItemCount() >= index) {
            filterData(mData);
            List<T> temp = new ArrayList<>();
            for (int i = index; i < index + itemCount; i++) {
                if (i >= mData.size()) {
                    break;
                }
                temp.add(mData.get(i));
            }
            mData.removeAll(temp);
            notifyItemRangeRemoved(index, temp.size());
        }
    }

    public void update(T data) {
        if (data != null && null != mData) {
            int startIndex = this.mData.indexOf(data);
            if (startIndex != -1) {
                mData.set(startIndex, data);
            }
            notifyItemChanged(startIndex);
        }
    }

    public void update(int index, T data) {
        if (null != mData && index >= 0 && index < mData.size() && data != null) {
            mData.set(index, data);
            filterData(this.mData);
            notifyItemChanged(index);
        }
    }

    public void filterData(List<T> data) {
        // Dummy
    }

    public void clearData() {
        if (null != mData) {
            this.mData.clear();
            notifyDataSetChanged();
        }
    }

    void setItemClickSupport(ItemClickSupport itemClickSupport) {
        this.mItemClickSupport = itemClickSupport;
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mItemClickSupport != null) {
                mItemClickSupport.onItemClick(view);
            }
        }
    };

    private View.OnLongClickListener mLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            return mItemClickSupport != null && mItemClickSupport.onItemLongClick(view);
        }
    };

    public void showErrorView(ErrorViewHolder.OnReloadClickListener reloadClickListener) {
        ICommonAdapterBean emptyBean = new ICommonAdapterBean() {
            @Override
            public int getViewType() {
                return ViewType.VIEW_TYPE_ERROR;
            }
        };
        if (mData == null) {
            mData = new ArrayList<>();
        } else {
            mData.clear();
        }
        mData.add((T)emptyBean);
        mReloadClickListener = reloadClickListener;
        notifyDataSetChanged();
    }
}