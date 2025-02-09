package com.example.test12.presentation.bucket

import io.github.jan.supabase.auth.user.UserInfo

data class BucketScreenState (
    var error: String? = null,
    var success: Boolean = false,
    var isLoading: Boolean = false,
)