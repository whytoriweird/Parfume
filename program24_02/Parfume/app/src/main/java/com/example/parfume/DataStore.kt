package com.example.parfume

object DataStore {
    // Це наша глобальна база товарів. Її бачать і Адмін, і Клієнти.
    val perfumes = mutableListOf(
        Perfume(1, "Versace", "Man Eau Fraiche", "2500 ₴", ""),
        Perfume(2, "Zara", "Vibrant Leather Boise", "1200 ₴", ""),
        Perfume(3, "Tom Ford", "Tobacco Vanille", "8000 ₴", ""),
        Perfume(4, "Dior", "Sauvage", "4000 ₴", "")
    )

    // А це глобальний кошик поточного клієнта
    val cart = mutableListOf<Perfume>()
}