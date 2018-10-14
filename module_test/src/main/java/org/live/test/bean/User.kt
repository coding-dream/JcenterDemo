package org.live.test.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by wl on 2018/10/14.
 */
data class User(var name: String, var age: Int, @SerializedName("address") var mAddress: String)

data class Result(var users: List<User>, var code: Int)