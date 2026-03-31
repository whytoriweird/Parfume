package com.example.parfume

// Модель одного замовлення
data class Order(
    val customerName: String,
    val itemsDescription: String,
    val totalPrice: String
)

object DataStore {
    // Глобальна база товарів
    val perfumes = mutableListOf(
        Perfume(1, "Versace", "Man Eau Fraiche", "2500 ₴", ""),
        Perfume(2, "Zara", "Vibrant Leather Boise", "1200 ₴", ""),
        Perfume(3, "Tom Ford", "Tobacco Vanille", "8000 ₴", ""),
        Perfume(4, "Dior", "Sauvage", "4000 ₴", "")
    )

    // Глобальний кошик поточного клієнта
    val cart = mutableListOf<Perfume>()

    // ЖУРНАЛ ЗАМОВЛЕНЬ (сюди будуть падати всі покупки)
    val orders = mutableListOf<Order>()
}