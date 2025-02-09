package com.example.test12.presentation.details

import com.example.test12.domain.category.Category
import com.example.test12.domain.shoes.Shoes
import io.github.jan.supabase.auth.user.UserInfo

data class DetailsScreenState (
    var category: String = "",
    var error: String? = null,
    var success: Boolean = false,
    var isLoading: Boolean = false,
    var userInfo: UserInfo? = null,
    var selectedShoesState: Shoes? = null
)