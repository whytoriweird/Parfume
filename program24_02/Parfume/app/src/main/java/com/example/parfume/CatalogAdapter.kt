package com.example.parfume

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CatalogAdapter(
    private val perfumes: MutableList<Perfume>,
    private val onDeleteClick: (Int) -> Unit,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {

    class CatalogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvBrand: TextView = view.findViewById(R.id.tvBrand)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)

        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_perfume_editable, parent, false)
        return CatalogViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val perfume = perfumes[position]
        holder.tvBrand.text = perfume.brand
        holder.tvName.text = perfume.name
        holder.tvPrice.text = perfume.price

        // Обробка кліку по смітнику
        holder.btnDelete.setOnClickListener {
            onDeleteClick(position)
        }

        // Обробка кліку по самій картці (для редагування)
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
    }

    override fun getItemCount() = perfumes.size
}