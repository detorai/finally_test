package com.example.test12.presentation.sign_up

data class SignUpScreenState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    var error: String? = null,
    var isLoading: Boolean = false,
    val checkState: Boolean = false,
    val isSignUp: Boolean = false
)
