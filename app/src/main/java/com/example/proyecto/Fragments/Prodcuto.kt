package com.example.proyecto.Fragments

import android.os.Parcel
import android.os.Parcelable

data class Prodcuto (

    val id: Int,
    val name: String,
    val descripcion:String,
    val precio: Double,
    val img: Int

): Parcelable{

    //Constructor secundario
    constructor(parcel: Parcel) : this (

        parcel.readInt(),
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(descripcion)
        parcel.writeDouble(precio)
        parcel.writeInt(img)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR: Parcelable.Creator<Prodcuto>{

        override fun createFromParcel(parcel: Parcel): Prodcuto {
            return Prodcuto(parcel)
        }

        override fun newArray(size: Int): Array<Prodcuto?> {
            return arrayOfNulls(size)
        }
    }
}

