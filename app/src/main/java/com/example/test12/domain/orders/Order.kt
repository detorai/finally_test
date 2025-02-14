package com.example.test12.domain.orders

import com.example.test12.domain.shoes.Shoes
import kotlinx.datetime.LocalDateTime

data class Order(
    val listShoes: List<Shoes>,
    val order_number: String,
    val order_name: String,
    val order_date: LocalDateTime,
    val user_number: String,
    val user_email: String,
    val user_addres: String,
    val walletCard: String
)
