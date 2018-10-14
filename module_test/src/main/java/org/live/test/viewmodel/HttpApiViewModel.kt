package org.live.test.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.live.baselib.http.HttpApiManager
import org.live.test.bean.Result
import org.live.test.util.RxUtils
/**
 * Created by wl on 2018/10/14.
 */
class HttpApiViewModel : ViewModel() {

    val userLiveData: MutableLiveData<Result> = MutableLiveData()

    fun loadUsers() {
        HttpApiManager.getHttpService().getUsers("xxxx", "2018-1-12")
                .map { userList -> Result(userList, 20) }
                .compose(RxUtils.getSchedulerTransformer())
                .subscribe(object : RxUtils.SimpleObserver<Result>() {

                    override fun onNext(t: Result) {
                        userLiveData.value = t
                    }

                })
    }
}