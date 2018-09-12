package org.live.baselib.rvsample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nono.android.modules.setting.luckdraw.bean.PageList
import com.nono.android.modules.setting.luckdraw.bean.ParticipateHistory
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter
import org.live.baselib.R
import org.live.baselib.rvmore.MySwipeRefreshLayout
import org.live.baselib.rvmore.RefreshLoadMoreWrapper

/**
 * Created by wl on 2018/9/10.
 *
 * 抽奖页面的共用逻辑
 */
abstract class BaseRefreshFragment : BaseLazyFragment(){

    lateinit var refreshLoadMoreWrapper: RefreshLoadMoreWrapper
    lateinit var adapter: MultiTypeAdapter
    val items: Items = Items()
    lateinit var baseViewModel: LuckDrawViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.nn_setting_luckdraw_fragment, container, false)
        baseViewModel = createViewModel(getViewModelClass())
        observerDataChange(baseViewModel)
        initView(rootView)
        return rootView
    }

    abstract fun observerDataChange(baseViewModel: LuckDrawViewModel)

    abstract fun<T : LuckDrawViewModel> getViewModelClass(): Class<T>

    private fun initView(rootView: View) {
        // 初始化下拉刷新加载更多
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val swipeRefreshLayout = rootView.findViewById<MySwipeRefreshLayout>(R.id.swipeRefreshLayout)

        // 初始化Adapter(Paging框架必须在initLoadMoreView前设置adapter)
        adapter = MultiTypeAdapter(items)
        initViewProvider(adapter)
        recyclerView.adapter = adapter

        // 初始化下拉刷新和加载更多
        refreshLoadMoreWrapper = RefreshLoadMoreWrapper()
        refreshLoadMoreWrapper.initRefreshView(activity, swipeRefreshLayout)
        refreshLoadMoreWrapper.initLoadMoreView(recyclerView, null)
        refreshLoadMoreWrapper.setRefreshCallback{
            baseViewModel.refreshData()
        }
        refreshLoadMoreWrapper.setLoadMoreCallback{
            baseViewModel.loadMoreData()
        }
        refreshLoadMoreWrapper.setHasLoadedAllData(true)
    }

    abstract fun initViewProvider(adapter: MultiTypeAdapter)

    /**
     * 执行时间在onActivityCreate之后
     */
    override fun onFirstUserVisible() {
        super.onFirstUserVisible()
        // 显示下拉刷新并回调onRefresh接口
        refreshLoadMoreWrapper.showRefreshState()
    }

    private fun<T : LuckDrawViewModel> createViewModel(clazz: Class<T>): T {
        val baseViewModel = ViewModelProviders.of(this).get(clazz)
        // 处理监听的状态
        baseViewModel.refreshStateLiveData.observe(this, Observer<Int> {
            when (it) {
                LuckDrawViewModel.STATE_REFRESH_COMPLETE -> {
                    refreshLoadMoreWrapper.setRefreshComplete()
                    items.clear()
                }
                LuckDrawViewModel.STATE_LOADMORE_COMPLETE -> {
                    refreshLoadMoreWrapper.setLoadMoreComplete()
                }
                LuckDrawViewModel.STATE_LOAD_FAILED -> {
                    refreshLoadMoreWrapper.setRefreshComplete()
                    refreshLoadMoreWrapper.setLoadMoreComplete()
                }
            }
        })

        baseViewModel.hasMoreStateLive.observe(this, Observer {
            refreshLoadMoreWrapper.setHasLoadedAllData(it!!)
        })
        return baseViewModel
    }
}

/**
 * 参与抽奖页面
 */
class ParticipateFragment : BaseRefreshFragment() {

    override fun initViewProvider(adapter: MultiTypeAdapter) {
        val viewProvider = ParticipateHistoryViewProvider()
        adapter.register(ParticipateHistory::class.java, viewProvider)
    }

    override fun observerDataChange(baseViewModel: LuckDrawViewModel) {
        // 处理监听的数据
        val participateViewModel = baseViewModel as ParticipateViewModel
        participateViewModel.participatesLiveData.observe(this, Observer { pageList: PageList<ParticipateHistory>? ->
            pageList?.models?.let { items.addAll(it) }
            adapter.notifyDataSetChanged()
        })
    }

    override fun<T : LuckDrawViewModel> getViewModelClass(): Class<T> {
        return ParticipateViewModel::class.java as Class<T>
    }
}