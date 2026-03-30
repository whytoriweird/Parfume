package com.example.parfume

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShopAdapter(
    private val perfumes: List<Perfume>,
    private val onAddToCartClick: (Perfume) -> Unit
) : RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    class ShopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvBrand: TextView = view.findViewById(R.id.tvShopBrand)
        val tvName: TextView = view.findViewById(R.id.tvShopName)
        val tvPrice: TextView = view.findViewById(R.id.tvShopPrice)
        val btnAddToCart: Button = view.findViewById(R.id.btnAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false)
        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val perfume = perfumes[position]
        holder.tvBrand.text = perfume.brand
        holder.tvName.text = perfume.name
        holder.tvPrice.text = perfume.price

        holder.btnAddToCart.setOnClickListener {
            onAddToCartClick(perfume)
        }
    }

    override fun getItemCount() = perfumes.size
}