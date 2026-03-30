package com.example.parfume

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Створюємо наші тестові парфуми
        val perfumes = listOf(
            Perfume(1, "Versace", "Man Eau Fraiche", "2 500 ₴", "Свіжий деревний аромат для літа"),
            Perfume(2, "Zara", "Vibrant Leather Boise", "1 200 ₴", "Стильний шкіряний аромат на кожен день"),
            Perfume(3, "Tom Ford", "Tobacco Vanille", "8 000 ₴", "Розкішний пряний аромат з нотами тютюну")
        )

        // 2. Знаходимо наш список на екрані
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // 3. Кажемо йому виводити елементи вертикально
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 4. Передаємо адаптер з нашими даними
        recyclerView.adapter = PerfumeAdapter(perfumes)
    }
}