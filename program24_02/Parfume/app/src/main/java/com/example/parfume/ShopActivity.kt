package com.example.parfume

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop) // Створи цей файл дизайну за аналогією з каталогом

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        val recyclerView = findViewById<RecyclerView>(R.id.rvShop)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Використовуємо наш спільний склад DataStore.perfumes
        recyclerView.adapter = ShopAdapter(DataStore.perfumes) { selectedPerfume ->
            // Додаємо товар у кошик
            DataStore.cart.add(selectedPerfume)
            Toast.makeText(this, "${selectedPerfume.name} додано у кошик!", Toast.LENGTH_SHORT).show()
        }
    }
}