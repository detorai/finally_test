package com.example.test12.data.remote_data_source.sales

import com.example.test12.data.remote_data_source.SupabaseClient
import io.github.jan.supabase.postgrest.from

class SalesRepository {
    suspend fun getSales() = SupabaseClient.client.from("Sales").select().decodeList<SalesDto>()
}