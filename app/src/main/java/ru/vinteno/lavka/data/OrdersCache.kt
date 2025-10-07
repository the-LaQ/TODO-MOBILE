package ru.vinteno.lavka.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object OrdersCache {
    private const val PREFS_NAME = "orders_cache_prefs"
    private const val KEY_ORDERS_JSON = "orders_json"
    private val gson = Gson()

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
        // Simple hardcoded fallback to ensure UI always has content
        return listOf(
            Order(
                id = 1,
                items = "Кола, Чипсы",
                delivery_address = "Общежитие 5, комната 312",
                status = "Новый"
            ),
            Order(
                id = 2,
                items = "Пицца 'Пепперони'",
                delivery_address = "Учебный корпус 3, ауд. 101",
                status = "Доставлен"
            ),
            Order(
                id = 3,
                items = "Тетради (5 шт.), Ручка",
                delivery_address = "Библиотека, читальный зал",
                status = "Новый"
            )
        )
    }
}



