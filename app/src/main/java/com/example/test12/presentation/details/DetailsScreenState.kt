package com.example.test12.presentation.details

import com.example.test12.domain.category.Category

data class DetailsScreenState (
    var category: List<Category> = emptyList()
)