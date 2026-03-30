package com.example.parfume

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(
    private val cartItems: MutableList<Perfume>,
    private val onRemoveClick: (Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvBrand: TextView = view.findViewById(R.id.tvCartBrand)
        val tvName: TextView = view.findViewById(R.id.tvCartName)
        val tvPrice: TextView = view.findViewById(R.id.tvCartPrice)
        val btnRemove: ImageButton = view.findViewById(R.id.btnRemoveFromCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val perfume = cartItems[position]
        holder.tvBrand.text = perfume.brand
        holder.tvName.text = perfume.name
        holder.tvPrice.text = perfume.price

        holder.btnRemove.setOnClickListener {
            onRemoveClick(position)
        }
    }

    override fun getItemCount() = cartItems.size
}