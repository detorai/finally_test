package com.example.test12.data.remote_data_source.notification

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class NotificationDto(
    val id: Long,
    val notif_label: String,
    val notif_content: String,
    val notif_time: LocalDateTime
)