package com.emreozcan.favoritecars.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.emreozcan.favoritecars.data.models.CarModel


@Dao
interface CarDao {
    @Query("SELECT * FROM car_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<CarModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(carModel: CarModel)

    @Query("DELETE FROM car_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteCar(carModel: CarModel)

    @Update
    suspend fun updateCar(carModel: CarModel)

}