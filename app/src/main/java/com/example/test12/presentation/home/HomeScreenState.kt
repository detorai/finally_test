package com.example.test12.presentation.home

import androidx.compose.material3.DrawerState
import com.example.test12.domain.category.Category
import com.example.test12.domain.sales.Sales
import io.github.jan.supabase.auth.user.UserInfo

data class HomeScreenState (
    val category: List<Category> = emptyList(),
    val sales: List<Sales> = emptyList(),
    var error: String? = null,
    var success: Boolean = false,
    var isLoading: Boolean = false,
    var userInfo: UserInfo? = null,
)
