package org.live.baselib.rvsample

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.nono.android.modules.setting.luckdraw.bean.PageList
import com.nono.android.modules.setting.luckdraw.bean.ParticipateHistory
import java.lang.Exception

/**
 * Created by wl on 2018/9/10.
 */
abstract class LuckDrawViewModel : ViewModel(){

    open val PAGE_SIZE = 20

    open var currPage = 1

    /**
     * 网络请求后的状态
     */
    companion object State {
        const val STATE_REFRESH_COMPLETE = 0x01
        const val STATE_LOADMORE_COMPLETE = 0x02
        const val STATE_LOAD_FAILED = 0x03
    }

    /**
     * 刷新状态 -> 用来更新显示 下拉刷新和加载更多的布局
     */
    val refreshStateLiveData: MutableLiveData<Int> = MutableLiveData()

    /**
     * 数据状态 -> 是否还有下一页数据,用于设置是否可以 加载更多
     */
    val hasMoreStateLive: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * 根据不同的type的下拉刷新请求
     */
    abstract fun refreshData()

    /**
     * 根据不同的type的加载更多请求
     */
    abstract fun loadMoreData()

    fun resetPage() {
        currPage = 1
    }

    fun plugPage() {
        currPage ++
    }
}

/**
 * 参与抽奖
 */
class ParticipateViewModel : LuckDrawViewModel() {

    val participatesLiveData by lazy { MutableLiveData<PageList<ParticipateHistory>>() }

    override fun refreshData() {
        resetPage()
        LuckDrawProtocol().getTestRequest(currPage, PAGE_SIZE, object : StringCallback {
            override fun success(html: String) {
                val type = object : TypeToken<PageList<ParticipateHistory>>() {}.type
                // gson很可能解析为空
                val pageList = Gson().fromJson<PageList<ParticipateHistory>>(html, type)?: PageList(0, ArrayList())
                refreshStateLiveData.value = STATE_REFRESH_COMPLETE
                participatesLiveData.value = pageList
                checkDataLave(pageList)
            }

            override fun failed() {
                refreshStateLiveData.value = STATE_LOAD_FAILED
                resetPage()
            }
        })
    }

    /**
     * 检测是否还有剩余数据
     */
    private fun checkDataLave(pageList: PageList<ParticipateHistory>) {
        val hasData = pageList.models.isEmpty()
        hasMoreStateLive.value = hasData
    }

    override fun loadMoreData() {
        LuckDrawProtocol().getTestRequest(currPage, PAGE_SIZE, object : StringCallback {
            override fun success(html: String) {
                try {
                    val type = object : TypeToken<PageList<ParticipateHistory>>() {}.type
                    val pageList = Gson().fromJson<PageList<ParticipateHistory>>(html, type)?: PageList(0, ArrayList())
                    refreshStateLiveData.value = STATE_LOADMORE_COMPLETE
                    participatesLiveData.value = pageList

                    checkDataLave(pageList)
                    plugPage()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun failed() {
                refreshStateLiveData.value = STATE_LOAD_FAILED
            }
        })
    }
}