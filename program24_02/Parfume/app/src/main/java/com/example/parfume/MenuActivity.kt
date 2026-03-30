package com.example.parfume

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        val tvRole = findViewById<TextView>(R.id.tvRole)

        // Знаходимо всі кнопки
        val btnAdminCatalog = findViewById<Button>(R.id.btnAdminCatalog)
        val btnAdminBrands = findViewById<Button>(R.id.btnAdminBrands)
        val btnClientShop = findViewById<Button>(R.id.btnClientShop)
        val btnClientCart = findViewById<Button>(R.id.btnClientCart)
        val btnClientProfile = findViewById<Button>(R.id.btnClientProfile)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        // Спочатку дізнаємося, хто зараз авторизований
        val currentLogin = sharedPref.getString("currentLogin", "") ?: ""

        // А тепер дістаємо ім'я саме ЦЬОГО користувача
        val firstName = sharedPref.getString("${currentLogin}_firstName", "Гість")
        val lastName = sharedPref.getString("${currentLogin}_lastName", "")

        tvGreeting.text = "Привіт, $firstName $lastName!"

        // ... (далі йде перевірка if (currentLogin.lowercase() == "admin") - вона залишається без змін)
        tvGreeting.text = "Привіт, $firstName $lastName!"

        // ПЕРЕВІРКА РОЛЕЙ
        if (currentLogin.lowercase() == "admin") {
            // Режим АДМІНІСТРАТОРА
            tvRole.text = "Роль: Адміністратор"

            // Показуємо адмінські кнопки
            btnAdminCatalog.visibility = View.VISIBLE
            btnAdminBrands.visibility = View.VISIBLE

            // Ховаємо клієнтські кнопки
            btnClientShop.visibility = View.GONE
            btnClientCart.visibility = View.GONE
            btnClientProfile.visibility = View.GONE

            btnAdminCatalog.setOnClickListener {
                startActivity(Intent(this, CatalogActivity::class.java))
            }
            // ЗАМІНИ TOAST НА ЦЕ:
            btnAdminBrands.setOnClickListener {
                startActivity(Intent(this, BrandsActivity::class.java))
            }

        } else {
            // Режим КЛІЄНТА
            tvRole.text = "Роль: Покупець"

            // Ховаємо адмінські кнопки
            btnAdminCatalog.visibility = View.GONE
            btnAdminBrands.visibility = View.GONE

            // Показуємо клієнтські кнопки
            btnClientShop.visibility = View.VISIBLE
            btnClientCart.visibility = View.VISIBLE
            btnClientProfile.visibility = View.VISIBLE

            btnClientShop.setOnClickListener {
                startActivity(Intent(this, ShopActivity::class.java))
            }
            btnClientCart.setOnClickListener {
                startActivity(Intent(this, CartActivity::class.java))
            }
            btnClientProfile.setOnClickListener {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
        }

        // Вихід з акаунта (однаковий для всіх)
        btnLogout.setOnClickListener {
            sharedPref.edit().putBoolean("isAuthorized", false).apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}