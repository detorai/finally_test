package com.example.test12.presentation.profile

data class ProfileScreenState(
    var isLoading: Boolean = false,
    var success: Boolean = false,
    var error: String? = null,
    var editState: Boolean = false,
    var choosePicture: Boolean = false,
    val name: String = "",
    val se_name: String = "",
    val address: String = "",
    val number: String = "",
    val image: String = "",
)