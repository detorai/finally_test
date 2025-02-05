package com.example.test12.domain.category

import com.example.test12.domain.shoes.Shoes

data class CategoryWithShoes(
    val cat_id: Long,
    val shoes: List<Shoes>
)
