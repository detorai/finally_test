package com.example.test12.presentation.change_password

data class ForgotPasswordScreenState(
    val email: String = "",
    var error: String? = null,
    var isLoading: Boolean = false,
    val success: Boolean = false
)
