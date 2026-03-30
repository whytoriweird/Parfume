package com.example.parfume

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etName = findViewById<EditText>(R.id.etRegName)
        val etSurname = findViewById<EditText>(R.id.etRegSurname)
        val etDob = findViewById<EditText>(R.id.etRegDob)
        val etEmail = findViewById<EditText>(R.id.etRegEmail)
        val etLogin = findViewById<EditText>(R.id.etRegLogin)
        val etPassword = findViewById<EditText>(R.id.etRegPassword)
        val etPasswordConfirm = findViewById<EditText>(R.id.etRegPasswordConfirm)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        // Логіка DatePicker для дати народження
        etDob.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val dateStr = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etDob.setText(dateStr)
            }, year, month, day).show()
        }

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val surname = etSurname.text.toString()
            val dob = etDob.text.toString()
            val email = etEmail.text.toString()
            val login = etLogin.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val passConfirm = etPasswordConfirm.text.toString().trim()

            // ВАЛІДАЦІЯ
            if (name.isEmpty() || surname.isEmpty() || dob.isEmpty() || email.isEmpty() || login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заповніть всі поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!email.contains("@") || !email.contains(".")) {
                Toast.makeText(this, "Неправильний формат Email!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(this, "Пароль має бути від 6 символів!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != passConfirm) {
                Toast.makeText(this, "Паролі не співпадають!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ЗБЕРЕЖЕННЯ В SharedPreferences
            // ЗБЕРЕЖЕННЯ ДЛЯ БАГАТЬОХ КОРИСТУВАЧІВ
            val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

            // Перевіряємо, чи такий логін вже не зайнятий кимось іншим
            if (sharedPref.contains("${login}_password")) {
                Toast.makeText(this, "Користувач з таким логіном вже існує!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Зберігаємо дані, додаючи логін як префікс до кожного ключа
            with(sharedPref.edit()) {
                putString("${login}_firstName", name)
                putString("${login}_lastName", surname)
                putString("${login}_dob", dob)
                putString("${login}_email", email)
                putString("${login}_password", password)
                apply()
            }

            Toast.makeText(this, "Реєстрація успішна!", Toast.LENGTH_SHORT).show()
            finish() // Закриваємо реєстрацію, повертаємось на Login
        }
    }
}