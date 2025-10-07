package ru.vinteno.lavka.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrdersRepository(
    private val api: OrdersApi
) {
    suspend fun loadNewOrders(context: Context): List<Order> = withContext(Dispatchers.IO) {
        try {
            val all = api.getOrders()
            // Save to cache
            OrdersCache.save(context, all)
            all.filter { it.status.equals("Новый", ignoreCase = true) }
        } catch (t: Throwable) {
            // Try cache first
            val cached = OrdersCache.load(context)
            val source = if (!cached.isNullOrEmpty()) cached else OrdersCache.loadBundledFallback()
            source.filter { it.status.equals("Новый", ignoreCase = true) }
        }
    }
}


