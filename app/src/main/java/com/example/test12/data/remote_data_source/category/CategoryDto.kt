package com.example.test12.data.remote_data_source.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto (
    val id: Long,
    val category_name: String
)
