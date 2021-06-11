package com.emreozcan.favoritecars.view.fragments

import android.graphics.Bitmap
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.emreozcan.favoritecars.R
import com.emreozcan.favoritecars.data.models.CarModel
import com.emreozcan.favoritecars.view.fragments.detail.DetailFragmentViewModel
import com.emreozcan.favoritecars.view.fragments.home.HomeFragmentDirections
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class BindingAdapters {
    companion object{


        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: ExtendedFloatingActionButton,navigate: Boolean){
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


        @BindingAdapter("android:setImageViewFromBitmap")
        @JvmStatic
        fun setImage(view: ImageView,bitmap: Bitmap){
            view.setImageBitmap(bitmap)
        }

        @BindingAdapter("android:isCheckBoxChecked")
        @JvmStatic
        fun isChecked(view: CheckBox,value: Boolean){
            view.isChecked = value
        }


        @BindingAdapter(value = ["carModel","detailFragmentViewModel"])
        @JvmStatic
        fun deleteCar(view: Button,carModel: CarModel,detailFragmentViewModel: DetailFragmentViewModel){
            view.setOnClickListener {
                detailFragmentViewModel.deleteCar(carModel)
                Toast.makeText(it.context,"Car Deleted !", Toast.LENGTH_SHORT).show()
                view.findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
            }
        }


        @BindingAdapter("android:sendDataToDetails")
        @JvmStatic
        fun startAction(view: CardView,carModel: CarModel){
            view.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(carModel)
                view.findNavController().navigate(action)
            }

        }
        @BindingAdapter("android:showStar")
        @JvmStatic
        fun showStar(view: ImageButton, isFavorite: Boolean){
            if(isFavorite) view.visibility = View.VISIBLE
        }



    }
}