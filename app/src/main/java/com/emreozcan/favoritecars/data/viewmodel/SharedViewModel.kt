package com.emreozcan.favoritecars.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.emreozcan.favoritecars.data.models.Colors

class SharedViewModel(application: Application): AndroidViewModel(application) {

    fun parseColor(color: String): Colors {
        return when(color){
            "White"->{
                Colors.White}
            "Black"->{
                Colors.Black}
            "Blue"->{
                Colors.Blue}
            "Red"->{
                Colors.Red}
            "Green"->{
                Colors.Green}
            "Yellow"->{
                Colors.Yellow}
            else->{
                Colors.Grey}
        }
    }

}