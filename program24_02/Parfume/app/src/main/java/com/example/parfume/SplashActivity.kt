package com.example.parfume

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Відкриваємо SharedPreferences
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val isAuthorized = sharedPref.getBoolean("isAuthorized", false)

        // Перевіряємо куди кидати юзера
        if (isAuthorized) {
            startActivity(Intent(this, MenuActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish() // Закриваємо Splash, щоб до нього не можна було повернутись кнопкою "Назад"
    }
}