package com.example.madcamp_1

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter.writeString

data class Event(var res:String?) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readString()) {
        parcel.run{
            res=readString().toString()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel?.run{
            writeString(this@Event.res)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
    override fun toString(): String {
        return res ?: ""
    }


}