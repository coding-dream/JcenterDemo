package org.live.baselib.rvmore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class NonoStaggeredGridLayoutManager extends StaggeredGridLayoutManager {
    public NonoStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public NonoStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    /**
     * 设置瀑布流布局中的某个item占满宽度
     *
     * @param itemView 该item的整个布局
     */
    public static void setStaggeredItemSpanFull(View itemView) {
        StaggeredGridLayoutManager.LayoutParams layoutParams =
                (LayoutParams) itemView.getLayoutParams();
        if (null == layoutParams) {
            layoutParams = new StaggeredGridLayoutManager.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        layoutParams.setFullSpan(true);
        itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            return super.scrollVerticallyBy(dy, recycler, state);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        try {
            super.onScrollStateChanged(state);
        } catch (Exception e) {
        }
    }
}
