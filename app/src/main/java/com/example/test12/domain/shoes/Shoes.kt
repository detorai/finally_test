package com.example.test12.domain.shoes

import com.example.test12.domain.category.Category

data class Shoes(
    val id: Long,
    val name: String,
    val cost: Double,
    val category: Long,
    val description: String,
    var image: String,
    var count: Int = 0,
    val isPopular: Boolean,
    var inBucket: Boolean = false,
    var isFavourite: Boolean = false
)
