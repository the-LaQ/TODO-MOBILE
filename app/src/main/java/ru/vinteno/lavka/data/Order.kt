package ru.vinteno.lavka.data

data class Order(
    val id: Int,
    val items: String,
    val delivery_address: String,
    val status: String
)



