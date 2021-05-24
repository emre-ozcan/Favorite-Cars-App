package com.emreozcan.favoritecars.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.emreozcan.favoritecars.data.models.CarModel

@Database(entities = [CarModel::class],version = 2,exportSchema = false)
@TypeConverters(Converter::class)
abstract class CarDatabase: RoomDatabase() {
    abstract fun carDao(): CarDao

    companion object{
        @Volatile
        private var INSTANCE: CarDatabase?=null
        fun getDatabase(context: Context): CarDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "car_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}