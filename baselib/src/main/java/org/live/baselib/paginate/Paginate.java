package org.live.baselib.paginate;

import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import org.live.baselib.paginate.abslistview.AbsListViewPaginate;
import org.live.baselib.paginate.recycler.RecyclerPaginate;

/**
 * 加载更多的ListView/RecyclerView控制器
 * source form github: https://github.com/MarkoMilos/Paginate
 * <p>
 * 使用方法:
 * 1.定义回调
 * Paginate.Callbacks callbacks = new Paginate.Callbacks() {
 *
 * @Override public void onLoadMore() {
 * // Load next page of data (e.g. network or database)
 * }
 * @Override public boolean isLoading() {
 * // Indicate whether new page loading is in progress or not
 * return loadingInProgress;
 * }
 * @Override public boolean hasLoadedAllItems() {
 * // Indicate whether all data (pages) are loaded or not
 * return hasLoadedAllItems;
 * }
 * };
 * <p>
 * 2.注意调用Paginate之前要先设置要列表的adapter.
 * RecyclerView:
 * Paginate.with(recyclerView, callbacks)
 * .setLoadingTriggerThreshold(4)//设置距离底部item个数开始加载数据 默认5
 * .addLoadingListItem(true)//设置是否需要加载底部loadingview
 * .setLoadingListItemCreator(new CustomLoadingListItemCreator())//设置底部loading的视图,可不设置
 * .setLoadingListItemSpanSizeLookup(new CustomLoadingListItemSpanLookup())//设置grid的lookup
 * .build();
 * <p>
 * private class CustomLoadingListItemCreator implements LoadingListItemCreator {
 * @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
 * LayoutInflater inflater = LayoutInflater.from(parent.getContext());
 * View view = inflater.inflate(R.layout.custom_loading_list_item, parent, false);
 * return new VH(view);
 * }
 * @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
 * // Bind custom loading row if needed
 * }
 * }
 * <p>
 * ListView:
 * Paginate.with(absListView, callbacks)
 * .setOnScrollListener(scrollListener) // Delegate scroll listener
 * .setLoadingTriggerThreshold(2)
 * .addLoadingListItem(true)
 * .setLoadingListItemCreator(new CustomLoadingListItemCreator())
 * .build();
 * <p>
 * Paginate instance
 * <p>
 * Calling build() upon Paginate.Builder will return Paginate instance which will allow you to:
 * <p>
 * unbind() - Call unbind to detach list (RecyclerView or AbsListView) from Paginate when pagination functionality
 * is no longer needed on the list. Paginate is using scroll listeners and adapter data observers in order to perform
 * required checks (when list is scrolled to the end or when new data is added to source adapter). It wraps original
 * (source) adapter with new adapter that provides loading row if loading row is used. When unbind is called original
 * adapter will be set on the list and scroll listeners and data observers will be detached. You need to call unbind()
 * if you re-setup recycler view (e.g. change adapter, layout manager etc)
 * <p>
 * setHasMoreDataToLoad(boolean) - if you are using loading row (which is default setup), each time when you add data
 * to adapter check will be performed and if there is no more data to load loading row will be removed. That means that
 * loading row will be added/removed automatically. Use this method to explicitly (manually) notify that there is no
 * more item to load.
 */
public abstract class Paginate {

    public interface Callbacks {
        /**
         * Called when next page of data needs to be loaded.
         */
        void onLoadMore();

        /**
         * Called to check if loading of the next page is currently in progress. While loading is in progress
         * {@link com.nono.android.common.paginate.Paginate.Callbacks#onLoadMore} won't be called.
         *
         * @return true if loading is currently in progress, false otherwise.
         */
        boolean isLoading();

        /**
         * Called to check if there is more data (more pages) to load. If there is no more pages to load, {@link
         * com.nono.android.common.paginate.Paginate.Callbacks#onLoadMore} won't be called and loading row, if used, won't be added.
         *
         * @return true if all pages has been loaded, false otherwise.
         */
        boolean hasLoadedAllItems();
    }

    /**
     * Use this method to indicate that there is or isn't more data to load. If there isn't any more data to load
     * loading row, if used, won't be displayed as the last item of the list. Adding/removing loading row is done
     * automatically each time when underlying adapter data is changed. Use this method to explicitly add/remove
     * loading row.
     *
     * @param hasMoreDataToLoad true if there is more data to load, false otherwise.
     */
    abstract public void setHasMoreDataToLoad(boolean hasMoreDataToLoad);

    /**
     * Call unbind to detach list (RecyclerView or AbsListView) from Paginate when pagination functionality is no
     * longer needed on the list.
     * <p>
     * Paginate is using scroll listeners and adapter data observers in order to perform required checks. It wraps
     * original (source) adapter with new adapter that provides loading row if loading row is used. When unbind is
     * called original adapter will be set on the list and scroll listeners and data observers will be detached.
     */
    abstract public void unbind();

    /**
     * Create pagination functionality upon RecyclerView.
     *
     * @param recyclerView RecyclerView that will have pagination functionality.
     * @param callback     pagination callbacks.
     * @return {@link com.nono.android.common.paginate.recycler.RecyclerPaginate.Builder}
     */
    public static RecyclerPaginate.Builder with(RecyclerView recyclerView, Callbacks callback) {
        return new RecyclerPaginate.Builder(recyclerView, callback);
    }

    /**
     * Create pagination functionality upon AbsListView.
     *
     * @param absListView AbsListView that will have pagination functionality (ListView or GridView).
     * @param callback    pagination callbacks.
     * @return {@link com.nono.android.common.paginate.abslistview.AbsListViewPaginate.Builder}
     */
    public static AbsListViewPaginate.Builder with(AbsListView absListView, Callbacks callback) {
        return new AbsListViewPaginate.Builder(absListView, callback);
    }
}