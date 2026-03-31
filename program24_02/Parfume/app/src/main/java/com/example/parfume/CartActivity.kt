package com.example.parfume

import android.content.Context
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

        // Налаштовує адаптер і передаємо йому функцію видалення
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
        // Кнопка оформлення замовлення
        btnCheckout.setOnClickListener {
            if (DataStore.cart.isEmpty()) {
                Toast.makeText(this, "Ваш кошик порожній!", Toast.LENGTH_SHORT).show()
            } else {
                // 1. Дістаємо ім'я поточного клієнта
                val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                val currentLogin = sharedPref.getString("currentLogin", "") ?: ""
                val customerName = sharedPref.getString("${currentLogin}_firstName", "Невідомий клієнт") ?: "Невідомий клієнт"

                // 2. Формуємо список того, що він купив (через кому)
                val itemsBought = DataStore.cart.joinToString(", ") { it.name }

                // 3. Беремо загальну суму
                val total = tvTotalPrice.text.toString()

                // 4. СТВОРЮЄМО ЗАМОВЛЕННЯ І КИДАЄМО В БАЗУ АДМІНУ
                DataStore.orders.add(Order(customerName, itemsBought, total))

                Toast.makeText(this, "Замовлення відправлено адміну!", Toast.LENGTH_LONG).show()

                // 5. Очищаємо кошик після покупки
                DataStore.cart.clear()
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