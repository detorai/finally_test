package com.example.test12.domain.notification

import kotlinx.datetime.LocalDateTime

data class Notification(
    val id: Long,
    val label: String,
    val content: String,
    val time: LocalDateTime
)
