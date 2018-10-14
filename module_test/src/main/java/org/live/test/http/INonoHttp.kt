package org.live.baselib.http

import io.reactivex.Observable
import org.live.test.bean.User
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by wl on 2018/10/14.
 */
interface INonoHttp {

    @GET("/tools/mockapi/9124/getUsers")
    fun getUsers(@Query("sign") sign: String,
                 @Query("date") date: String): Observable<List<User>>

}