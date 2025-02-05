package com.example.test12.presentation.home

import com.example.test12.domain.category.Category
import com.example.test12.domain.sales.Sales

data class HomeScreenState (
    val category: List<Category> = emptyList(),
    val sales: List<Sales> = emptyList()
)
