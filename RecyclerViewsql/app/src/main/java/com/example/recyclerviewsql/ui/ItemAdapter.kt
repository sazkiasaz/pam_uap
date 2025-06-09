package com.example.recyclerviewsql.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewsql.database.ItemEntity
import com.practicum.litesql.R

class ItemAdapter(
    private val itemList: MutableList<ItemEntity>,
    private val onItemDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textItem: TextView = itemView.findViewById(R.id.textItem)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)

        init {
            buttonDelete.setOnClickListener {
                onItemDeleteClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.textItem.text = itemList[position].name
    }

    override fun getItemCount(): Int = itemList.size
}
