package com.example.test12.data.remote_data_source.shoes

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ShoesDto(
    val id: Long,
    val shoes_name: String,
    val shoes_cost: Double,
    val shoes_url: String,
    val shoes_description: String,
    val IsPopular: Boolean,
    val CategoryId: Long
)


@Serializable
data class UserBucketDto(
    val user_uuid: String,
    val shoes_id: Long,
    val shoes_count: Int
)

@Serializable
data class UserFavouriteDto(
    val user_uuid: String,
    val shoes_id: Long,
)

@Serializable
data class ShoesId(
    val shoes_id: Long
)