package ru.vinteno.lavka.data

data class Order(
    val id: Int,
    val products: String,
    val name: String,
    val status: String? = "Новый",
    val dateTime: String
)



