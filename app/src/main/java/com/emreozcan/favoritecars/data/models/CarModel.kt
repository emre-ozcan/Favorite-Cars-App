package com.emreozcan.favoritecars.data.models

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "car_table")
data class CarModel (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var name: String,
    var color : Colors,
    var hp: Int,
    var maxSpeed: Int,
    var year: Int,
    var isFavorite: Boolean,
    var image:Bitmap
    ):Parcelable