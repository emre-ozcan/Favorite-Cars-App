package com.emreozcan.favoritecars.view.fragments.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.emreozcan.favoritecars.data.models.CarModel

class CarDiffUtil(
    private val oldList: List<CarModel>,
    private val newList: List<CarModel>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                &&oldList[oldItemPosition].name == newList[newItemPosition].name
                &&oldList[oldItemPosition].color == newList[newItemPosition].color
                &&oldList[oldItemPosition].hp == newList[newItemPosition].hp
                &&oldList[oldItemPosition].maxSpeed == newList[newItemPosition].maxSpeed
                &&oldList[oldItemPosition].year == newList[newItemPosition].year
                &&oldList[oldItemPosition].isFavorite == newList[newItemPosition].isFavorite
                &&oldList[oldItemPosition].image == newList[newItemPosition].image

    }
}