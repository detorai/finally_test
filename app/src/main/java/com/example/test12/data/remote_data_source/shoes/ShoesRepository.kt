package com.example.test12.data.remote_data_source.shoes

import com.example.test12.data.remote_data_source.SupabaseClient
import com.example.test12.domain.shoes.Shoes
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class ShoesRepository {

    suspend fun getShoes() =
        SupabaseClient.client.from("Shoes").select(columns = Columns.ALL).decodeList<ShoesDto>()
}