package org.live.test.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

/**
 * Created by wl on 2018/10/14.
 */
data class User(var name: String, var age: Int, @SerializedName("address") var mAddress: String, var type: Int = 1) : MultiItemEntity {

    override fun getItemType(): Int {
        return type
    }

    fun getSpanSize(): Int {
        return when (type) {
            TYPE_ITEM_ONE -> 1
            TYPE_ITEM_NOMAL, TYPE_ITEM_VIP -> 2
            else -> 1
        }
    }

    companion object {
        const val TYPE_ITEM_ONE = 1
        const val TYPE_ITEM_NOMAL= 2
        const val TYPE_ITEM_VIP= 3
    }
}

data class Result(var users: List<User>, var code: Int)