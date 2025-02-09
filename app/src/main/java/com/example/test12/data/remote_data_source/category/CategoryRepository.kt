package com.example.test12.data.remote_data_source.category

import com.example.test12.data.remote_data_source.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class CategoryRepository {
    suspend fun getAllCategory() = SupabaseClient.client.from("Category").select(Columns.ALL).decodeList<CategoryDto>()
    suspend fun getCategoryById() = SupabaseClient.client.from("Category").select(Columns.ALL).decodeList<CategoryDto>()

}