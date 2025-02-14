package com.example.test12.presentation.notification

import com.example.test12.domain.notification.Notification

data class NotificationScreenState (
    val success: Boolean =false,
    val isLoading: Boolean =false,
    val error: String? =null,
    var notifList: List<Notification> = emptyList()
)