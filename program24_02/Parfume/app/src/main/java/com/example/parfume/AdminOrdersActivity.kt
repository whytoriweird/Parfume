package com.example.parfume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// 1. Адаптер для списку замовлень
class OrdersAdapter(private val ordersList: List<Order>) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCustomer: TextView = view.findViewById(R.id.tvOrderCustomer)
        val tvItems: TextView = view.findViewById(R.id.tvOrderItems)
        val tvTotal: TextView = view.findViewById(R.id.tvOrderTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = ordersList[position]
        holder.tvCustomer.text = "Клієнт: ${order.customerName}"
        holder.tvItems.text = "Товари: ${order.itemsDescription}"
        holder.tvTotal.text = "Сума: ${order.totalPrice}"
    }

    override fun getItemCount() = ordersList.size
}

// 2. Сам екран
class AdminOrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_orders)

        val btnBack = findViewById<Button>(R.id.btnBackOrders)
        btnBack.setOnClickListener { finish() }

        val rvOrders = findViewById<RecyclerView>(R.id.rvOrders)
        rvOrders.layoutManager = LinearLayoutManager(this)

        // Передаємо адаптеру список з нашого журналу DataStore.orders
        rvOrders.adapter = OrdersAdapter(DataStore.orders)
    }
}