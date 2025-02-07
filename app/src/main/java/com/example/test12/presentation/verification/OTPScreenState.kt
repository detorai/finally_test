package com.example.test12.presentation.verification

data class OTPScreenState (
    val otpCode: String = "",
    var isLoading: Boolean = false,
    var success: Boolean = false,
    val dialog: String? = null,
    val newPassword: String = "",
    var passwordState: Boolean = false
)