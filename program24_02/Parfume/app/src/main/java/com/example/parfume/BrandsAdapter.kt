package com.example.parfume

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BrandsAdapter(
    private val brands: List<Brand>,
    private val onItemClick: (Brand) -> Unit
) : RecyclerView.Adapter<BrandsAdapter.BrandViewHolder>() {

    class BrandViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvBrandName: TextView = view.findViewById(R.id.tvBrandName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_brand, parent, false)
        return BrandViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        val brand = brands[position]
        holder.tvBrandName.text = brand.name

        // Клік по бренду
        holder.itemView.setOnClickListener {
            onItemClick(brand)
        }
    }

    override fun getItemCount() = brands.size
}