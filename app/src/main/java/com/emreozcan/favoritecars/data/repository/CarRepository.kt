package com.emreozcan.favoritecars.data.repository

import androidx.lifecycle.LiveData
import com.emreozcan.favoritecars.data.db.CarDao
import com.emreozcan.favoritecars.data.models.CarModel


class CarRepository(private val carDao: CarDao) {

    val getAllData:LiveData<List<CarModel>> = carDao.getAllData()

    suspend fun insertData(carModel: CarModel){
        carDao.insertData(carModel)
    }

    suspend fun deleteAllDatabase(){
        carDao.deleteAll()
    }

}