package org.live.baselib.rvmore;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class MomentItemSpaceDecoration extends RecyclerView.ItemDecoration {
    private int halfSpace;
    private int extraHeader = 0;// 其他类型的Header数量

    public MomentItemSpaceDecoration(int space) {
        this.halfSpace = space / 2;
    }

    public MomentItemSpaceDecoration(int space, int extraHeader) {
        this.halfSpace = space / 2;
        this.extraHeader = extraHeader;
    }

    public void setExtraHeader(int extraHeader) {
        this.extraHeader = extraHeader;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int layoutPosition = lp.getViewLayoutPosition();
        int spanIndex = lp.getSpanIndex();

        if (extraHeader > 0 && layoutPosition < extraHeader) {
            outRect.left = 0;
            outRect.right = 0;
            outRect.top = 0;
            outRect.bottom = 0;
            return;
        }

        int realPosition = layoutPosition - extraHeader;
        if (spanIndex == 0) {
            outRect.left = 0;
            outRect.right = halfSpace;
        } else {
            outRect.left = halfSpace;
            outRect.right = 0;
        }

        if (realPosition < 2) {
            outRect.top = 2*halfSpace;
        } else {
            outRect.top = halfSpace;
        }
        outRect.bottom = halfSpace;
    }
}
