package com.example.parfume

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CatalogActivity : AppCompatActivity() {

    private lateinit var adapter: CatalogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        // РОБИМО КНОПКУ НАЗАД
        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish() // Закриває цей екран і повертає в Меню
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rvCatalog)
        val btnAddPerfume = findViewById<Button>(R.id.btnAddPerfume)

        adapter = CatalogAdapter(DataStore.perfumes,
            onDeleteClick = { position ->
                DataStore.perfumes.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, DataStore.perfumes.size)
            },
            onItemClick = { position ->
                showEditDialog(position)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAddPerfume.setOnClickListener {
            showAddDialog()
        }
    }

    // ПРОКАЧАНЕ ВІКНО ДОДАВАННЯ
    private fun showAddDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Додати парфум")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val inputBrand = EditText(this)
        inputBrand.hint = "Бренд (напр. Dior)"
        layout.addView(inputBrand)

        val inputName = EditText(this)
        inputName.hint = "Назва (напр. Sauvage)"
        layout.addView(inputName)

        val inputPrice = EditText(this)
        inputPrice.hint = "Ціна (напр. 4000 ₴)"
        layout.addView(inputPrice)

        builder.setView(layout)

        builder.setPositiveButton("Додати") { dialog, _ ->
            val brand = inputBrand.text.toString().takeIf { it.isNotBlank() } ?: "Невідомий бренд"
            val name = inputName.text.toString().takeIf { it.isNotBlank() } ?: "Без назви"
            val price = inputPrice.text.toString().takeIf { it.isNotBlank() } ?: "0 ₴"

            DataStore.perfumes.add(Perfume(DataStore.perfumes.size + 1, brand, name, price, ""))
            adapter.notifyItemInserted(DataStore.perfumes.size - 1)
            dialog.dismiss()
        }
        builder.setNegativeButton("Скасувати") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    // ПРОКАЧАНЕ ВІКНО РЕДАГУВАННЯ
    private fun showEditDialog(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Редагувати парфум")

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val oldPerfume = DataStore.perfumes[position]

        val inputBrand = EditText(this)
        inputBrand.setText(oldPerfume.brand)
        layout.addView(inputBrand)

        val inputName = EditText(this)
        inputName.setText(oldPerfume.name)
        layout.addView(inputName)

        val inputPrice = EditText(this)
        inputPrice.setText(oldPerfume.price)
        layout.addView(inputPrice)

        builder.setView(layout)

        builder.setPositiveButton("Зберегти") { dialog, _ ->
            val newBrand = inputBrand.text.toString().takeIf { it.isNotBlank() } ?: oldPerfume.brand
            val newName = inputName.text.toString().takeIf { it.isNotBlank() } ?: oldPerfume.name
            val newPrice = inputPrice.text.toString().takeIf { it.isNotBlank() } ?: oldPerfume.price

            DataStore.perfumes[position] = oldPerfume.copy(brand = newBrand, name = newName, price = newPrice)
            adapter.notifyItemChanged(position)
            dialog.dismiss()
        }
        builder.setNegativeButton("Скасувати") { dialog, _ -> dialog.cancel() }
        builder.show()
    }
}