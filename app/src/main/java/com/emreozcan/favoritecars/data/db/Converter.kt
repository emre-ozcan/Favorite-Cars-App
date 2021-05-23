package com.emreozcan.favoritecars.data.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.emreozcan.favoritecars.data.models.Colors
import java.io.ByteArrayOutputStream

class Converter {

    @TypeConverter
    fun fromColors(color: Colors): String{
        return color.name
    }

    @TypeConverter
    fun toColors(color: String): Colors{
        return Colors.valueOf(color)
    }

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray{
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream)
        return outputStream.toByteArray()
    }
    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap{
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }

}