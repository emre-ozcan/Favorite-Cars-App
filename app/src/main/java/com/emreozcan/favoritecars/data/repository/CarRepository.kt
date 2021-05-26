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

    suspend fun deleteCar(carModel: CarModel){
        carDao.deleteCar(carModel)
    }

    suspend fun updateCar(carModel: CarModel){
        carDao.updateCar(carModel)
    }

    fun searchItems(query: String): LiveData<List<CarModel>>{
        return carDao.searchItems(query)
    }

    fun searchForFastestCar():LiveData<List<CarModel>>{
        return carDao.searchForFastestCar()
    }

    fun searchForSlowestCar():LiveData<List<CarModel>>{
        return carDao.searchForSlowestCar()
    }

    fun searchForColor(): LiveData<List<CarModel>>{
        return carDao.searchForColor()
    }

    fun searchFavorites(): LiveData<List<CarModel>>{
        return carDao.searchFavorites()
    }

}