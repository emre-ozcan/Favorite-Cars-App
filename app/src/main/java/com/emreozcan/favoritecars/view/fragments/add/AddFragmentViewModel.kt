package com.emreozcan.favoritecars.view.fragments.add

import android.app.Application
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.emreozcan.favoritecars.R
import com.emreozcan.favoritecars.data.db.CarDatabase
import com.emreozcan.favoritecars.data.models.CarModel
import com.emreozcan.favoritecars.data.repository.CarRepository
import com.emreozcan.favoritecars.view.fragments.coloradapter.ColorAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class AddFragmentViewModel(application: Application): AndroidViewModel(application) {
    private val carDao = CarDatabase.getDatabase(application).carDao()
    private val repository: CarRepository = CarRepository(carDao)

    fun insertCar(carModel: CarModel){
        viewModelScope.launch(Dispatchers.IO ) {
            repository.insertData(carModel)
        }

    }

}