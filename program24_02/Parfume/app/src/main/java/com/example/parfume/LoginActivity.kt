package com.example.parfume

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etLogin = findViewById<EditText>(R.id.etLogin)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGoToRegister = findViewById<Button>(R.id.btnGoToRegister)

        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        btnLogin.setOnClickListener {
            val login = etLogin.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            // Шукаємо пароль саме для ВВЕДЕНОГО логіна
            val savedPass = sharedPref.getString("${login}_password", null)

            if (savedPass != null && pass == savedPass && login.isNotEmpty()) {
                // Успішний вхід! Обов'язково запам'ятовуємо, ХТО саме зараз сидить
                sharedPref.edit()
                    .putBoolean("isAuthorized", true)
                    .putString("currentLogin", login)
                    .apply()

                startActivity(Intent(this, MenuActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Невірний логін або пароль!", Toast.LENGTH_SHORT).show()
            }
        }

        btnGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}