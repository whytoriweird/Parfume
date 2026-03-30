package com.example.parfume

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PerfumeAdapter(private val perfumes: List<Perfume>) : RecyclerView.Adapter<PerfumeAdapter.PerfumeViewHolder>() {

    class PerfumeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvBrand: TextView = view.findViewById(R.id.tvBrand)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val btnAddToCart: Button = view.findViewById(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfumeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_perfume, parent, false)
        return PerfumeViewHolder(view)
    }

    override fun onBindViewHolder(holder: PerfumeViewHolder, position: Int) {
        val perfume = perfumes[position]
        holder.tvBrand.text = perfume.brand
        holder.tvName.text = perfume.name
        holder.tvPrice.text = perfume.price
        holder.tvDescription.text = perfume.description

        holder.btnAddToCart.setOnClickListener {
            // Тут ми потім додамо логіку додавання в кошик
        }
    }

    override fun getItemCount() = perfumes.size
}