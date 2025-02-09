package com.example.test12.data.remote_data_source.shoes

import com.example.test12.data.remote_data_source.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class ShoesRepository {

    suspend fun getShoes() =
        SupabaseClient.client.from("Shoes").select(columns = Columns.ALL).decodeList<ShoesDto>()

    suspend fun getShoesByCategory(categoryId: Long) = SupabaseClient.client.from("Shoes").select(Columns.ALL){
        filter {
            eq("CategoryId", categoryId)
        }
    }.decodeList<ShoesDto>()

    //popular
    suspend fun getPopular() = SupabaseClient.client.from("Shoes").select(Columns.ALL){
        filter {
            eq("IsPopular", true)
        }
    }.decodeList<ShoesDto>()


    //favourite
    suspend fun getFavourite(userId: String) =
        SupabaseClient.client.from("fafourite_user").select(Columns.list("shoes_id")){
            filter {
                eq("user_uuid", userId)
            }
        }.decodeList<ShoesId>()
    suspend fun setFavourite(userFavouriteDto: UserFavouriteDto) =
        SupabaseClient.client.from("fafourite_user").insert(userFavouriteDto)

    suspend fun removeFavourite(userFavouriteDto: UserFavouriteDto) =
        SupabaseClient.client.from("fafourite_user").delete(){
            filter {
                and {
                    eq("user_uuid", userFavouriteDto.user_uuid)
                    eq("shoes_id", userFavouriteDto.shoes_id)
                }
            }
        }


    //bucket
    suspend fun getBucket(userId: String) =
        SupabaseClient.client.from("bucket").select(Columns.ALL){
            filter {
                eq("user_uuid", userId)
            }
        }.decodeList<UserBucketDto>()
    suspend fun setBucket(userBucketDto: UserBucketDto) =
        SupabaseClient.client.from("bucket").insert(userBucketDto)

    suspend fun removeBucket(userBucketDto: UserBucketDto) =
        SupabaseClient.client.from("bucket").delete(){
            filter {
                and {
                    eq("user_uuid", userBucketDto.user_uuid)
                    eq("shoes_id", userBucketDto.shoes_id)
                    eq("shoes_count", userBucketDto.shoes_count)
                }
            }
        }
    suspend fun changeCount(userBucketDto: UserBucketDto) =
        SupabaseClient.client.from("bucket").update(
            {
                set("shoes_count", userBucketDto.shoes_count)
            }
        ) {
            filter {
                and {
                    eq("user_uuid", userBucketDto.user_uuid)
                    eq("shoes_id", userBucketDto.shoes_id)
                }
            }
        }
}