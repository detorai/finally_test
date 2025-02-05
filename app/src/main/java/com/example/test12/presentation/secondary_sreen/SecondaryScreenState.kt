package com.example.test12.presentation.secondary_sreen

import com.example.test12.domain.category.Category

data class SecondaryScreenState (
    val category: List<Category> = emptyList(),
    var selectedCategory: Category? = null,
    val label: String = "",
    var currentScreen: ScreenType
)
