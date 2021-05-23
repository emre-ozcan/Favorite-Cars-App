package com.emreozcan.favoritecars.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emreozcan.favoritecars.data.models.CarModel


@Dao
interface CarDao {
    @Query("SELECT * FROM car_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<CarModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(carModel: CarModel)
}