package com.example.project1

import android.graphics.Color
import android.os.Bundle
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class GridActivity : AppCompatActivity() {

    // Наші кольори: 0 - Жовтий, 1 - Червоний, 2 - Зелений
    private val colorYellow = Color.parseColor("#FFEB3B")
    private val colorRed = Color.parseColor("#F44336")
    private val colorGreen = Color.parseColor("#4CAF50")
    private val colors = arrayOf(colorYellow, colorRed, colorGreen)

    // Масив, який зберігає поточний індекс кольору для всіх 15 клітинок
    private val cellColors = IntArray(15)

    // Поточний рівень гри
    private var currentLevel = 1

    private lateinit var gridLayout: GridLayout
    private lateinit var tvLevel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)

        gridLayout = findViewById(R.id.myGridLayout)
        tvLevel = findViewById(R.id.tvLevel)

        // Налаштовуємо кліки по клітинках (робимо це лише один раз)
        for (i in 0 until 15) {
            val cell = gridLayout.getChildAt(i)
            cell.setOnClickListener {
                // Міняємо колір на наступний
                cellColors[i] = (cellColors[i] + 1) % 3
                cell.setBackgroundColor(colors[cellColors[i]])

                // Після кожного кліку перевіряємо, чи виграв гравець
                checkWinCondition()
            }
        }

        // Запускаємо перший рівень
        startLevel(1)
    }

    private fun startLevel(level: Int) {
        currentLevel = level
        tvLevel.text = "Рівень $currentLevel"

        // Рандомно розфарбовуємо поле на початку рівня (щоб не було одразу виграшної комбінації)
        do {
            for (i in 0 until 15) {
                cellColors[i] = (0..2).random()
                gridLayout.getChildAt(i).setBackgroundColor(colors[cellColors[i]])
            }
        } while (isWin()) // Якщо випадково випала переможна комбінація - перемішуємо знову
    }

    private fun checkWinCondition() {
        if (isWin()) {
            showVictoryDialog()
        }
    }

    // ТУТ НАША ЛОГІКА РІВНІВ
    private fun isWin(): Boolean {
        return when (currentLevel) {
            1 -> {
                // Рівень 1: Всі клітинки ОДНИМ кольором
                val firstColor = cellColors[2]
                cellColors.all { it == 2 }
            }
            2 -> {
                // Рівень 2: 1 колонка - жовті (0), 2 - червоні (1), 3 - зелені (2)
                var win = true
                for (i in 0 until 15) {
                    if (cellColors[i] != i % 3) win = false
                }
                win
            }
            3 -> {
                // Рівень 3: В кожному рядочку (з 5) має бути рівно 1 червоний колір (індекс 1)
                var win = true
                for (row in 0..4) {
                    // Рахуємо червоні у конкретному рядочку
                    val redCount = (0..2).count { col -> cellColors[row * 3 + col] == 1 }
                    if (redCount != 1) win = false
                }
                win
            }
            4 -> {
                // Рівень 4: Сусідні кольори не повторюються (ні по горизонталі, ні по вертикалі)
                var win = true
                for (i in 0 until 15) {
                    val row = i / 3
                    val col = i % 3

                    // Перевіряємо сусіда справа
                    if (col < 2 && cellColors[i] == cellColors[i + 1]) win = false
                    // Перевіряємо сусіда знизу
                    if (row < 4 && cellColors[i] == cellColors[i + 3]) win = false
                }
                win
            }
            else -> false
        }
    }

    // Діалогове вікно після проходження рівня
    private fun showVictoryDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Чудово!")
        builder.setMessage("Рівень $currentLevel пройдено успішно.")
        builder.setCancelable(false) // Щоб не можна було закрити кліком мимо вікна

        if (currentLevel < 4) {
            // Кнопка наступного рівня
            builder.setPositiveButton("Наступний рівень") { _, _ ->
                startLevel(currentLevel + 1)
            }
        } else {
            builder.setMessage("Вітаю, ти пройшов всю гру!")
        }

        builder.setNeutralButton("Наново (Цей рівень)") { _, _ ->
            startLevel(currentLevel)
        }

        builder.setNeutralButton("Почати гру спочатку") { _, _ ->
            startLevel(1)

        }
        if()

        builder.setNegativeButton("Закрити програму") { _, _ ->
            finishAffinity() // Закриває додаток повністю
        }

        builder.show()
    }
}