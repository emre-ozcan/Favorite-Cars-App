package com.emreozcan.favoritecars.view.fragments.coloradapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.emreozcan.favoritecars.R
import java.util.*


class ColorAdapter(context: Context,colorList: ArrayList<String> ) : ArrayAdapter<String>(context,0,colorList) {
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val color = getItem(position)

        val rowView = inflater.inflate(R.layout.dropdown_item,parent,false)

        val colorText = rowView.findViewById(R.id.drop_text) as TextView
        colorText.text = color

        val colorCard = rowView.findViewById(R.id.drop_card) as CardView
        when(color){
            "Black" -> colorCard.setCardBackgroundColor(Color.BLACK)
            "Blue" -> colorCard.setCardBackgroundColor(Color.BLUE)
            "Red" -> colorCard.setCardBackgroundColor(Color.RED)
            "Green" -> colorCard.setCardBackgroundColor(Color.GREEN)
            "Yellow" -> colorCard.setCardBackgroundColor(Color.YELLOW)
            "Grey" -> colorCard.setCardBackgroundColor(Color.GRAY)

        }
        return rowView
    }
}