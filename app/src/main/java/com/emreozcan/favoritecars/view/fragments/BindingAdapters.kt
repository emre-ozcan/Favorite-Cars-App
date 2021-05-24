package com.emreozcan.favoritecars.view.fragments

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.emreozcan.favoritecars.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {
    companion object{
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton,navigate: Boolean){
            view.setOnClickListener {
                if (navigate){
                    view.findNavController().navigate(R.id.action_homeFragment_to_addFragment)
                }
            }
        }
        @BindingAdapter("android:isEmptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View,emptyDatabase: MutableLiveData<Boolean>){
            when(emptyDatabase.value){
                true->view.visibility = View.VISIBLE
                false->view.visibility= View.INVISIBLE
            }
        }
    }
}