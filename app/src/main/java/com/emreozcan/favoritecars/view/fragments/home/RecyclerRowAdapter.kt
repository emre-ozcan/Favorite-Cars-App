package com.emreozcan.favoritecars.view.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.emreozcan.favoritecars.R
import com.emreozcan.favoritecars.data.models.CarModel
import com.emreozcan.favoritecars.databinding.RowRecyclerDesignBinding
import kotlinx.android.synthetic.main.row_recycler_design.view.*
import java.util.ArrayList

class RecyclerRowAdapter: RecyclerView.Adapter<RecyclerRowAdapter.ViewHolder>(){
    private var dataList = emptyList<CarModel>()

    class ViewHolder(private val binding: RowRecyclerDesignBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(car: CarModel){
            binding.car = car
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowRecyclerDesignBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCar = dataList[position]
        holder.bind(currentCar)
    }

    fun setDataToRecycler(carList: ArrayList<CarModel>){
        dataList = carList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}