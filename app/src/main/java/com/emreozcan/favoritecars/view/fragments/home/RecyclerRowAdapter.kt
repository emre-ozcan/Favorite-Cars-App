package com.emreozcan.favoritecars.view.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emreozcan.favoritecars.R
import com.emreozcan.favoritecars.data.models.CarModel
import com.emreozcan.favoritecars.databinding.RowRecyclerDesignBinding
import kotlinx.android.synthetic.main.row_recycler_design.view.*
import java.util.ArrayList

class RecyclerRowAdapter: RecyclerView.Adapter<RecyclerRowAdapter.ViewHolder>(){
    var dataList = emptyList<CarModel>()

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_recycler_design,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.cardTitle.text = dataList[position].name
        holder.itemView.cardHp.text = "${dataList[position].hp} hp"
        holder.itemView.cardMaxSpeed.text = "${dataList[position].maxSpeed} km/h"
        holder.itemView.cardTime.text = dataList[position].year.toString()
        holder.itemView.cardImage.setImageBitmap(dataList[position].image)
        if (dataList[position].isFavorite){
            holder.itemView.favoriteStar.visibility = View.VISIBLE
        }
    }

    fun setDataToRecycler(carList: ArrayList<CarModel>){
        dataList = carList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}