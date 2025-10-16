package ru.vinteno.lavka.data

import retrofit2.http.GET

interface OrdersApi {
    @GET("orders")
    suspend fun getOrders(): List<Order>
}



