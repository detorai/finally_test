package com.example.test12.presentation.orders

import com.example.test12.domain.orders.Order

data class OrdersScreenState (
    val error: String? = null,
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val orders: List<Order> = emptyList()
)