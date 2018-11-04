package org.live.test.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by wl on 2018/11/2.
 */
class TestUser(val name: String): Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestUser> {
        override fun createFromParcel(parcel: Parcel): TestUser {
            return TestUser(parcel)
        }

        override fun newArray(size: Int): Array<TestUser?> {
            return arrayOfNulls(size)
        }
    }
}