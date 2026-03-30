package com.example.parfume

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BrandsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brands)

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        // Наша інформаційна база брендів
        val brandsList = listOf(
            Brand("Versace", "Італійський дім моди, заснований Джанні Версаче у 1978 році. Їх парфуми відрізняються яскравими, розкішними та зухвалими нотами."),
            Brand("Zara", "Іспанський бренд, що належить Inditex. Відомий своїми доступними ароматами, які часто нагадують преміальні нішеві парфуми."),
            Brand("Tom Ford", "Американський бренд. Том Форд створює ексклюзивні, дуже стійкі та дорогі аромати, використовуючи рідкісні інгредієнти, такі як тютюн, уд та шкіра."),
            Brand("Dior", "Французький дім моди. Їх лінійка Sauvage стала світовим бестселером завдяки свіжому, але дуже чоловічому шлейфу."),
            Brand("Chanel", "Класика французької парфумерії. Bleu de Chanel — еталон деревно-цитрусових ароматів для чоловіків.")
        )

        val recyclerView = findViewById<RecyclerView>(R.id.rvBrands)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = BrandsAdapter(brandsList) { clickedBrand ->
            showBrandInfoDialog(clickedBrand)
        }
    }

    // Виклик Pop-up вікна з історією
    private fun showBrandInfoDialog(brand: Brand) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(brand.name)
        builder.setMessage(brand.description)
        builder.setPositiveButton("Зрозуміло") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}