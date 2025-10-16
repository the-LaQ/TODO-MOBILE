package ru.vinteno.lavka.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object OrdersCache {
    private const val PREFS_NAME = "orders_cache_prefs"
    private const val KEY_ORDERS_JSON = "orders_json"
    private val gson = Gson()

    @SuppressLint("UseKtx")
    fun save(context: Context, orders: List<Order>) {
        val json = gson.toJson(orders)
        context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            .edit()
            .putString(KEY_ORDERS_JSON, json)
            .apply()
    }

    fun load(context: Context): List<Order>? {
        val json = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            .getString(KEY_ORDERS_JSON, null)
        if (json.isNullOrBlank()) return null
        val type = object : TypeToken<List<Order>>() {}.type
        return runCatching { gson.fromJson<List<Order>>(json, type) }.getOrNull()
    }

    fun loadBundledFallback(): List<Order> {
        // Заказы
        return listOf(
//            Order(
//                id = 1,
//                products = "Кола, Чипсы",
//                name = "Общежитие 5, комната 312",
//                status = "Новый",
//                dateTime = "2025-10-06T19:00:00"
//            ),
//            Order(
//                id = 2,
//                products ="Пицца 'Пепперони'",
//                name = "Учебный корпус 3, ауд. 101",
//                status = "Доставлен",
//                dateTime = "2025-10-06T19:00:00"
//            ),
//            Order(
//                id = 3,
//                products = "Тетради (5 шт.), Ручка",
//                name = "Библиотека, читальный зал",
//                status = "Новый",
//                dateTime = "2025-10-06T19:00:00"
//            ),
//
//            Order(
//                id = 4,
//                products = "Вода минеральная, Сок апельсиновый",
//                name = "Общежитие 3, комната 205",
//                status = "Новый",
//                dateTime = "2025-10-06T19:00:00"
//            ),
//            Order(
//                id = 5,
//                products = "Шоколад Milka, M&M's",
//                name = "Учебный корпус 1, ауд. 405",
//                status = "Новый",
//                dateTime = "2025-10-06T19:00:00"
//            ),
//            Order(
//                id = 6,
//                products = "Бургер, Картофель фри, Кола",
//                name = "Библиотека, 2 этаж",
//                status = "Новый",
//                dateTime = "2025-10-06T19:00:00"
//            ),
//            Order(
//                id = 7,
//                products = "Кофе Латте, Круассан",
//                name =  "Спортивный комплекс, раздевалка",
//                status = "Новый",
//                dateTime = "2025-10-06T19:00:00"
//            ),

        )
    }
}



