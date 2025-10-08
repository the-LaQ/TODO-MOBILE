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
            Order(
                id = 1,
                products = "Кола, Чипсы",
                name = "Общежитие 5, комната 312",
                status = "Новый",
                dateTime = "2025-10-06T19:00:00"
            )
        )
    }
}



