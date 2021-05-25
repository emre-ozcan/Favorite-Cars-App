package com.emreozcan.favoritecars.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.emreozcan.favoritecars.data.db.CarDatabase
import com.emreozcan.favoritecars.data.models.CarModel
import com.emreozcan.favoritecars.data.repository.CarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class HomeFragmentViewModel(application: Application): AndroidViewModel(application){


    private val carDao = CarDatabase.getDatabase(application).carDao()
    private val repository: CarRepository = CarRepository(carDao)

    val getAllData: LiveData<List<CarModel>> =repository.getAllData
    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkDatabaseEmpty(carData: List<CarModel>){
        emptyDatabase.value = carData.isEmpty()
    }

    fun deleteDatabase(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllDatabase()
        }
    }
    fun deleteCar(carModel: CarModel){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteCar(carModel)
        }
    }
    fun insertCar(carModel: CarModel){
        viewModelScope.launch(Dispatchers.IO ) {
            repository.insertData(carModel)
        }

    }

    fun searchDatabase(query: String):LiveData<List<CarModel>>{
        return repository.searchItems(query)
    }
}