package com.example.madcamp_1

import android.os.Parcel
import android.os.Parcelable

data class Phone(var name:String, var phone_Number:String, var fav_Food:String, var dis_Food:String,var address:String,var photo:String)
    :Parcelable{
    /*
        var name:String=""
        var phone_Number:String=""
        var fav_Food:String=""
        var dis_Food:String=""
        var address:String=""
        var photo:String=""
*/
    constructor(parcel: Parcel):this("","","","","",""){
        parcel.run {
            name = readString().toString()
            phone_Number = readString().toString()
            fav_Food = readString().toString()
            dis_Food = readString().toString()
            address = readString().toString()
            photo = readString().toString()
        }
        }
        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest?.run {
                writeString(this@Phone.name)
                writeString(this@Phone.phone_Number)
                writeString(this@Phone.fav_Food)
                writeString(this@Phone.dis_Food)
                writeString(this@Phone.address)
                writeString(this@Phone.photo)

            }
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Phone> {
            override fun createFromParcel(parcel: Parcel): Phone {
                return Phone(parcel)
            }

            override fun newArray(size: Int): Array<Phone?> {
                return arrayOfNulls(size)
            }
        }

    }