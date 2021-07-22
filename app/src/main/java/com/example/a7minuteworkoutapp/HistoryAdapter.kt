package com.example.a7minuteworkoutapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_item_row.view.*

class HistoryAdapter(val context: Context, val items:ArrayList<String>):RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.history_item_row,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = items[position]
        holder.tvId.text = (position+1).toString()
        holder.tv_date.text = date
    }

    override fun getItemCount(): Int {
        return items.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val llItemHostory = itemView.ll_item_history_row
        val tvId = itemView.tv_id
        val tv_date = itemView.tv_date
    }
}