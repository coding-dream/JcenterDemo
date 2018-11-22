package org.live.test.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import kotlinx.android.synthetic.main.activity_pulltorefresh.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.live.baselib.toast
import org.live.baselib.util.SystemUtils
import org.live.test.R
import org.live.test.adapter.UserAdapter
import org.live.test.bean.User
import org.live.test.database.Logger
import org.live.test.widget.MyVerticalLoadMoreView

/**
 * Created by wl on 2018/11/22.
 */
class PullToRefreshActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private val userAdapter = UserAdapter(mutableListOf())
    private var currPage = 1
    private val PAGE_SIZE = 5

    private lateinit var notDataView: View
    private lateinit var errorView: View
    private lateinit var loadingView: View
    private lateinit var headerView: View

    /**
     * 网络请求后的状态
     */
    companion object State {
        const val STATE_REFRESH_COMPLETE = 0x01
        const val STATE_LOADMORE_COMPLETE = 0x02
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pulltorefresh)
        initView()
        onRefresh()
    }

    private fun initView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        // 下拉刷新的高度
        swipeRefreshLayout.setProgressViewEndTarget(true, SystemUtils.dip2px(this, 82f))
        // 设置手指在屏幕下拉多少距离会触发下拉刷新
        swipeRefreshLayout.setDistanceToTriggerSync(dip(120))
        recycleView.layoutManager = GridLayoutManager(this, 3)
        recycleView.adapter = userAdapter
        swipeRefreshLayout.setOnRefreshListener(this)
        userAdapter.setSpanSizeLookup{ _, position -> userAdapter.data[position].getSpanSize() }

        userAdapter.setOnLoadMoreListener(this, recycleView)
        userAdapter.setLoadMoreView(MyVerticalLoadMoreView())
        userAdapter.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
            val userEntity = userAdapter.getItem(position)
            toast(userEntity!!.name)
        }

        notDataView = layoutInflater.inflate(R.layout.empty_view, recycleView.parent as ViewGroup, false)
        errorView = layoutInflater.inflate(R.layout.error_view, recycleView.parent as ViewGroup, false)
        loadingView = layoutInflater.inflate(R.layout.loading_view, recycleView.parent as ViewGroup, false)

        errorView.setOnClickListener { onRefresh() }

        headerView = layoutInflater.inflate(R.layout.header_view, recycleView.parent as ViewGroup, false)
        userAdapter.addHeaderView(headerView)
    }

    override fun onRefresh() {
        refreshData()
    }

    override fun onLoadMoreRequested() {
        loadMoreData()
    }

    private fun refreshData() {
        userAdapter.emptyView = loadingView

        userAdapter.setEnableLoadMore(false)
        currPage = 1
        requestData()
    }

    private fun loadMoreData() {
        swipeRefreshLayout.isEnabled = false
        requestData()
    }

    private var errorIndex = 0

    private fun requestData() {
        Logger.d("errorIndex: $errorIndex")

        val refreshStated = if(currPage == 1) STATE_REFRESH_COMPLETE else STATE_LOADMORE_COMPLETE

        doAsync {
            val datas = mutableListOf<User>()
            for (i in 0..5) {
                val user1 = User("name: $i", i, "", User.TYPE_ITEM_ONE)
                datas.add(user1)
                val user2 = User("name: $i", i, "", User.TYPE_ITEM_NOMAL)
                datas.add(user2)
                val user3 = User("name: $i", i, "", User.TYPE_ITEM_VIP)
                datas.add(user3)

            }

            Thread.sleep(1000)
            uiThread {
                if (errorIndex < 4) {
                    onDataSuccess(datas, refreshStated)
                    // 设置EmptyView
                    userAdapter.emptyView = notDataView

                } else {
                    onDataFailed(refreshStated)
                    // 设置EmptyView
                    userAdapter.emptyView = errorView
                }
                errorIndex ++
                // 修改enable状态
                if (refreshStated == STATE_REFRESH_COMPLETE) {
                    userAdapter.setEnableLoadMore(true)
                } else if (refreshStated == STATE_LOADMORE_COMPLETE){
                    swipeRefreshLayout.isEnabled = true
                }

            }
        }
    }

    private fun checkDataLave(pageList: MutableList<User>) {
        val hasMoreData: Boolean = pageList.size >= PAGE_SIZE
        var noMoreStateLiveData = !hasMoreData
        if (noMoreStateLiveData) {
            stopLoadEnd()
        }
    }

    private fun onDataSuccess(datas: MutableList<User>, refreshStated: Int) {
        currPage ++

        when (refreshStated) {
            STATE_REFRESH_COMPLETE -> {
                userAdapter.setNewData(datas)
                stopRefreshing()
            }
            STATE_LOADMORE_COMPLETE -> {
                userAdapter.addData(datas)
                stopLoadMore()
            }
        }

        // 是否加载了全部数据
        checkDataLave(datas)
    }

    private fun onDataFailed(refreshStated: Int) {
        stopRefreshing()
        stopLoadEnd()
    }

    private fun stopRefreshing() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun stopLoadMore() {
        userAdapter.loadMoreComplete()
    }

    private fun stopLoadFaild() {
        userAdapter.loadMoreFail()
    }

    private fun stopLoadEnd() {
        userAdapter.loadMoreEnd()
        userAdapter.setEnableLoadMore(false)
    }
}
