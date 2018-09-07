package org.live.baselib.rvmore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import org.live.baselib.R;
import org.live.baselib.paginate.Paginate;
import org.live.baselib.rvmore.ptr.PtrDefaultHandler;
import org.live.baselib.rvmore.ptr.PtrFrameLayout;
import org.live.baselib.util.SystemUtils;

/**
 * 下拉刷新和上拉分页的包装类
 *
 * @AUTHOR: yangjiantong
 * @DATE: 16/3/20
 * 使用方法:
 * 1.调用 initXXXView()
 * 2.设置回调 setXXXCallback()
 * 3.加载完成设置完成状态 setXXXComplete()
 */
public class RefreshLoadMoreWrapper {

    private boolean isEnableRefresh = false;//是否可以下拉刷新标记
    private boolean isEnableLoadMore = false;//是否可以上拉加载更多标记

    private PtrFrameLayout ptrFrameLayout;//下拉刷新组件
    private MySwipeRefreshLayout swipeRefreshLayout;//下拉刷新组件
    private Paginate paginate;//分页加载组件控制器

    private RefreshCallback refreshCallback;//下拉刷新的回调
    private LoadMoreCallback loadMoreCallback;//上拉加载更多的回调

    private boolean isRefreshing = false;//是否正在下拉刷新数据中...
    private boolean isLoadingMore = false;//是否正在上拉加载更多数据中...

    private boolean hasLoadedAllData = true;//是否加载完了所有数据(不再分页)

    private int refreshState = RefreshState.NORMAL_STATE;//加载数据的状态:下拉刷新,上拉加载更多,普通(默认)

    /**
     * 加载更多回调实现类
     */
    class PaginateCallback implements Paginate.Callbacks {
        @Override
        public void onLoadMore() {
            if (isRefreshing || isLoadingMore) {
                return;
            }
            isLoadingMore = true;
            refreshState = RefreshState.LOAD_MORE_STATE;
            if (loadMoreCallback != null) {
                loadMoreCallback.onLoadMore();
            }
        }

        @Override
        public boolean isLoading() {
            //返回是否正在加载更多中的状态
            return isLoadingMore;
        }

        @Override
        public boolean hasLoadedAllItems() {
            //判断是否加载完了所有的分页数据
            return hasLoadedAllData;
        }
    }

    /**
     * 初始化下拉刷新的PtrFrameLayout
     *
     * @param ptrFrameLayout
     */
    public void initRefreshView(PtrFrameLayout ptrFrameLayout) {
        this.ptrFrameLayout = ptrFrameLayout;
        if (ptrFrameLayout != null) {
            ptrFrameLayout.setResistance(1.7f);
            ptrFrameLayout.setKeepHeaderWhenRefresh(true);
            ptrFrameLayout.disableWhenHorizontalMove(true);
            ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
                @Override
                public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                    doRefresh();
                }
            });
//            ptrFrameLayout.setPullToRefresh(true);
        }
    }

    /**
     * 初始化下拉刷新的SwipeRefreshLayout
     *
     * @param context
     * @param swipeRefreshLayout
     */
    public void initRefreshView(Context context, MySwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        if (swipeRefreshLayout != null) {
//            swipeRefreshLayout.setColorSchemeResources(R.color.google_blue,
//                    R.color.google_red,
//                    R.color.google_green,
//                    R.color.google_yellow);
            swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
            //设置下拉的距离，参数1：下拉时圆圈是否缩放; 参数2，下拉的距离px
            swipeRefreshLayout.setProgressViewEndTarget(true, SystemUtils.dip2px(context, 82));
//            swipeRefreshLayout.setDistanceToTriggerSync(84);
            swipeRefreshLayout.setOnRefreshListener(new MySwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    doRefresh();
                }
            });

        }
    }

    private void doRefresh() {
        if (isRefreshing) return;
        if (isLoadingMore) {
            setRefreshComplete();
            return;
        }
        isRefreshing = true;
        refreshState = RefreshState.REFRESH_STATE;
        if (refreshCallback != null) {
            refreshCallback.onRefresh();
        }
    }

    /**
     * 设置下拉刷新完成(只针对单次刷新)
     */
    public void setRefreshComplete() {
        if (checkEnableRefresh()) {
            isRefreshing = false;
            if (ptrFrameLayout != null) {
                ptrFrameLayout.refreshComplete();
            }
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
            refreshState = RefreshState.NORMAL_STATE;
        }
    }

    /**
     * 非通过下拉手势操作来打开刷新View和刷新状态
     */
    public void showRefreshState() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
        doRefresh();
    }


    /**
     * 初始化分页的RecyclerView
     *
     * @param recyclerView
     * @param onScrollListener
     */
    public void initLoadMoreView(RecyclerView recyclerView, RecyclerView.OnScrollListener onScrollListener) {
        paginate = Paginate.with(recyclerView, new PaginateCallback())
                .setLoadingTriggerThreshold(4)
                .addLoadingListItem(true)
                .setOnScrollListener(onScrollListener)
                .build();
    }

    public void initLoadMoreView(RecyclerView recyclerView, int threshold) {
        paginate = Paginate.with(recyclerView, new PaginateCallback())
                .setLoadingTriggerThreshold(threshold)
                .addLoadingListItem(true)
                .build();
    }

    /**
     * 初始化分页的ListView
     *
     * @param listView
     */
    public void initLoadMoreView(ListView listView) {
        paginate = Paginate.with(listView, new PaginateCallback())
                .setLoadingTriggerThreshold(4)
                .addLoadingListItem(true)
                .build();
    }


    /**
     * 设置是否已经加载完了所有数据,不再分页加载
     *
     * @param hasLoadedAllData true:加载完了所有数据 false:还需继续分页加载
     */
    public void setHasLoadedAllData(boolean hasLoadedAllData) {
        if (checkEnableLoadMore()) {
            this.hasLoadedAllData = hasLoadedAllData;
            paginate.setHasMoreDataToLoad(!hasLoadedAllData);
        }
    }

    /**
     * 设置加载更多完成(只针对单次加载)
     */
    public void setLoadMoreComplete() {
        if (checkEnableLoadMore()) {
            isLoadingMore = false;
            refreshState = RefreshState.NORMAL_STATE;
        }
    }

    private boolean checkEnableRefresh() {
        return isEnableRefresh &&
                (ptrFrameLayout != null || swipeRefreshLayout != null);
    }

    private boolean checkEnableLoadMore() {
        return isEnableLoadMore && paginate != null;
    }

    /**
     * 获取加载数据的状态
     *
     * @return
     */
    public int getRefreshState() {
        return this.refreshState;
    }

    public void setRefreshState(int state) {
        this.refreshState = state;
    }

    /**
     * 设置下拉刷新回调接口
     *
     * @param callback
     */
    public void setRefreshCallback(RefreshCallback callback) {
        this.refreshCallback = callback;
        if (callback != null) {
            isEnableRefresh = true;
        }
    }

    /**
     * 设置上拉分页的回调接口
     *
     * @param callback
     */
    public void setLoadMoreCallback(LoadMoreCallback callback) {
        this.loadMoreCallback = callback;
        if (callback != null) {
            isEnableLoadMore = true;
        }
    }

    /**
     * 下拉刷新回调接口
     */
    public interface RefreshCallback {
        void onRefresh();
    }

    /**
     * 上拉加载更多接口
     */
    public interface LoadMoreCallback {
        void onLoadMore();
    }

    /**
     * 加载数据的状态
     * 下拉刷新,上拉加载更多,普通加载(默认)
     */
    public interface RefreshState {
        int REFRESH_STATE = 0x100;
        int LOAD_MORE_STATE = 0x101;
        int NORMAL_STATE = 0x102;
    }
}
