package com.example.test12.presentation.sign_in

data class SignInScreenState (
    val email: String = "",
    val password: String = "",
    var error: String? = null,
    var isSignIn: Boolean = false,
    var isLoading: Boolean = false,
    var passwordVisible: Boolean = false,
)