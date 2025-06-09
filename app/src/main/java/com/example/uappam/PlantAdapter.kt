package com.example.uappam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PlantAdapter(
    private val plantList: MutableList<Plant>,
    private val onDetailClick: (Plant) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    inner class PlantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_plant_name)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_plant_price)
        val ivImage: ImageView = itemView.findViewById(R.id.iv_plant_image)
        val btnDetail: Button = itemView.findViewById(R.id.btn_detail)
        val btnHapus: Button = itemView.findViewById(R.id.btn_hapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        return PlantViewHolder(view)
    }

    override fun getItemCount(): Int = plantList.size

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = plantList[position]
        holder.tvName.text = plant.name
        holder.tvPrice.text = if (plant.price.startsWith("Rp")) plant.price else "Rp ${plant.price}"

        when {
            !plant.imageUrl.isNullOrEmpty() -> {
                holder.ivImage.visibility = View.VISIBLE
                Glide.with(holder.itemView.context)
                    .load(plant.imageUrl)
                    .placeholder(R.drawable.bg_container)
                    .into(holder.ivImage)
            }
            plant.imageResId != null -> {
                holder.ivImage.visibility = View.VISIBLE
                holder.ivImage.setImageResource(plant.imageResId)
            }
            else -> {
                holder.ivImage.visibility = View.GONE
            }
        }

        holder.btnDetail.setOnClickListener {
            onDetailClick(plant)
        }

        holder.btnHapus.setOnClickListener {
            onDeleteClick(position)
        }
    }

    fun removeItem(position: Int) {
        plantList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(plant: Plant) {
        plantList.add(plant)
        notifyItemInserted(plantList.size - 1)
    }
}
