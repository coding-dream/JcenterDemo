package org.live.test.widget

import com.chad.library.adapter.base.loadmore.LoadMoreView
import org.live.test.R

class MyVerticalLoadMoreView : LoadMoreView() {

    override fun getLayoutId(): Int = R.layout.zz_vertical_load_more_view

    override fun getLoadingViewId(): Int = R.id.zz_load_more_loading_view

    override fun getLoadEndViewId(): Int = 0

    override fun getLoadFailViewId(): Int = R.id.zz_load_more_failed_view
}