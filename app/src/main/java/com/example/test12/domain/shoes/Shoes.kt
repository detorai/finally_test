package com.example.test12.domain.shoes

import com.example.test12.domain.category.Category

data class Shoes(
    val id: Long,
    val name: String,
    val cost: Double,
    val category: List<Category> = emptyList(),
    val description: String,
    val image: String,
    var count: Int = 0,
    var inBucket: Boolean = false,
    var isFavourite: Boolean = false
)
