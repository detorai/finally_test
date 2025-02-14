package com.example.test12.data.remote_data_source.notification

import com.example.test12.data.remote_data_source.SupabaseClient
import io.github.jan.supabase.postgrest.from

class NotificationRepository {
    suspend fun getNotification() =
        SupabaseClient.client.from("Notification").select().decodeList<NotificationDto>()
}