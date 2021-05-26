package com.emreozcan.favoritecars.data.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.emreozcan.favoritecars.R
import com.emreozcan.favoritecars.data.db.CarDatabase
import com.emreozcan.favoritecars.data.models.CarModel
import com.emreozcan.favoritecars.data.repository.CarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailFragmentViewModel(application: Application): AndroidViewModel(application){
    private val carDao = CarDatabase.getDatabase(application).carDao()
    private val repository: CarRepository = CarRepository(carDao)


    fun deleteCar(carModel: CarModel){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteCar(carModel)
        }
    }

    fun updateCar(carModel: CarModel){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCar(carModel)
        }
    }


}