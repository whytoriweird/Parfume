package com.example.parfume

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {

    private lateinit var adapter: CartAdapter
    private lateinit var tvTotalPrice: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val btnBackCart = findViewById<Button>(R.id.btnBackCart)
        btnBackCart.setOnClickListener { finish() }

        tvTotalPrice = findViewById(R.id.tvTotalPrice)
        val btnCheckout = findViewById<Button>(R.id.btnCheckout)
        val rvCart = findViewById<RecyclerView>(R.id.rvCart)

        // Налаштовуємо адаптер і передаємо йому функцію видалення
        adapter = CartAdapter(DataStore.cart) { position ->
            DataStore.cart.removeAt(position)
            adapter.notifyItemRemoved(position)
            adapter.notifyItemRangeChanged(position, DataStore.cart.size)
            updateTotalPrice() // Оновлюємо суму після видалення
        }

        rvCart.layoutManager = LinearLayoutManager(this)
        rvCart.adapter = adapter

        // Рахуємо суму при першому відкритті
        updateTotalPrice()

        // Кнопка оформлення замовлення
        btnCheckout.setOnClickListener {
            if (DataStore.cart.isEmpty()) {
                Toast.makeText(this, "Ваш кошик порожній!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Замовлення успішно оформлено!", Toast.LENGTH_LONG).show()
                DataStore.cart.clear() // Очищаємо кошик після покупки
                adapter.notifyDataSetChanged()
                updateTotalPrice()
            }
        }
    }

    // Хитра функція, яка витягує тільки цифри з ціни і рахує суму
    private fun updateTotalPrice() {
        var total = 0
        for (perfume in DataStore.cart) {
            // Беремо рядок ціни (напр. "2500 ₴"), видаляємо всі не-цифри і перетворюємо в Int
            val priceNumber = perfume.price.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
            total += priceNumber
        }
        tvTotalPrice.text = "Разом: $total ₴"
    }
}