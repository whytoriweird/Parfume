package com.example.parfume

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class ProfileActivity : AppCompatActivity() {

    private lateinit var ivAvatar: ImageView

    // Сучасний спосіб отримати фото з камери (без зайвих дозволів)
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        if (bitmap != null) {
            ivAvatar.setImageBitmap(bitmap)
        }
    }

    // Сучасний спосіб вибрати фото з галереї
    private val pickGalleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            ivAvatar.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnBackProfile = findViewById<Button>(R.id.btnBackProfile)
        btnBackProfile.setOnClickListener { finish() }

        ivAvatar = findViewById(R.id.ivAvatar)
        val btnCamera = findViewById<Button>(R.id.btnCamera)
        val btnGallery = findViewById<Button>(R.id.btnGallery)

        val etName = findViewById<EditText>(R.id.etProfileName)
        val etEmail = findViewById<EditText>(R.id.etProfileEmail)
        val etDob = findViewById<EditText>(R.id.etProfileDob)
        val btnSave = findViewById<Button>(R.id.btnSaveProfile)

        // 1. ЗАВАНТАЖЕННЯ ДАНИХ
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val currentLogin = sharedPref.getString("currentLogin", "") ?: ""

        val savedName = sharedPref.getString("${currentLogin}_firstName", "")
        val savedEmail = sharedPref.getString("${currentLogin}_email", "")
        val savedDob = sharedPref.getString("${currentLogin}_dob", "")

        etName.setText(savedName)
        etEmail.setText(savedEmail)
        etDob.setText(savedDob)

        // 2. КАЛЕНДАР ДЛЯ ДАТИ НАРОДЖЕННЯ
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

        // 3. КНОПКИ ФОТО
        btnCamera.setOnClickListener {
            takePictureLauncher.launch(null) // Запускає камеру
        }

        btnGallery.setOnClickListener {
            pickGalleryLauncher.launch("image/*") // Запускає галерею
        }

        // 4. ЗБЕРЕЖЕННЯ ДАНИХ
        btnSave.setOnClickListener {
            val newName = etName.text.toString().trim()
            val newEmail = etEmail.text.toString().trim()
            val newDob = etDob.text.toString().trim()

            if (newName.isEmpty() || newEmail.isEmpty() || newDob.isEmpty()) {
                Toast.makeText(this, "Заповніть всі поля!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Оновлюємо базу даних для поточного логіна
            sharedPref.edit()
                .putString("${currentLogin}_firstName", newName)
                .putString("${currentLogin}_email", newEmail)
                .putString("${currentLogin}_dob", newDob)
                .apply()

            Toast.makeText(this, "Профіль оновлено!", Toast.LENGTH_SHORT).show()
            finish() // Закриваємо профіль, повертаємось в меню
        }
    }
}