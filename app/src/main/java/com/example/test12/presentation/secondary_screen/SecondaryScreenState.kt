package com.example.test12.presentation.secondary_screen

import com.example.test12.domain.category.Category
import io.github.jan.supabase.auth.user.UserInfo

data class SecondaryScreenState (
    val category: List<Category> = emptyList(),
    var selectedCategory: Category? = null,
    val label: String = "",
    var currentScreen: ScreenType,
    var error: String? = null,
    var success: Boolean = false,
    var isLoading: Boolean = false,
)
