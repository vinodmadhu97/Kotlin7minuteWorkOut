package com.example.a7minuteworkoutapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise_status.view.*
import kotlin.contracts.Returns

class ExerciseStatusAdapter(val context:Context, val itemList: ArrayList<ExerciseModel>) : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_exercise_status,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvView.text = itemList[position].getId().toString()

        if (itemList[position].getIsSelected()){
            holder.tvView.background = ContextCompat.getDrawable(context,R.drawable.item_color_thin_color_accent_border)
            holder.tvView.setTextColor(Color.parseColor("#212121"))
        }else if(itemList[position].getIsCompleted()){
            holder.tvView.background = ContextCompat.getDrawable(context,R.drawable.item_circular_color_accent_background)
            holder.tvView.setTextColor(Color.parseColor("#ffffff"))
        }else{
            holder.tvView.background = ContextCompat.getDrawable(context,R.drawable.item_circular_color_gray_background)
            holder.tvView.setTextColor(Color.parseColor("#212121"))
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvView = itemView.tv_item
    }
}