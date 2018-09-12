package com.nono.android.modules.setting.luckdraw.bean

import org.live.baselib.rvsample.BaseEntity

/**
 * Created by wl on 2018/9/11.
 */
data class PageList<T>(var totalRows: Int, var models: List<T>) : BaseEntity