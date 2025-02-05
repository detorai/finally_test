package com.example.test12.presentation.sign_in

data class SignInScreenState (
    val email: String = "",
    val password: String = "",
    var Error: String? = null,
    var isSignIn: Boolean = false,
    var isLoading: Boolean = false
)