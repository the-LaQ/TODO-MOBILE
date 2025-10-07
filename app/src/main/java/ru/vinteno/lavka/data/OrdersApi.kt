package ru.vinteno.lavka.data

import retrofit2.http.GET

interface OrdersApi {
    @GET("api/orders")
    suspend fun getOrders(): List<Order>
}



