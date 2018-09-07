package org.live.baselib.paginate.recycler;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.live.baselib.R;

/** RecyclerView creator that will be called to create and bind loading list item */
public interface LoadingListItemCreator {

    /**
     * Create new loading list item {@link android.support.v7.widget.RecyclerView.ViewHolder}.
     *
     * @param parent   parent ViewGroup.
     * @param viewType type of the loading list item.
     * @return ViewHolder that will be used as loading list item.
     */
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * Bind the loading list item.
     *
     * @param holder   loading list item ViewHolder.
     * @param position loading list item position.
     */
    void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    LoadingListItemCreator DEFAULT = new LoadingListItemCreator() {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nn_common_foot_loading, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            // No binding for default loading row
            if (holder.itemView != null) {
                ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams staggerLp = (StaggeredGridLayoutManager.LayoutParams) lp;
                    staggerLp.setFullSpan(true);
                    holder.itemView.setLayoutParams(staggerLp);
                }
            }
        }
    };
}