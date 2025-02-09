package com.example.test12.domain.shoes

import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.data.local_data_source.ShoesEntity
import com.example.test12.data.remote_data_source.shoes.ShoesRepository
import com.example.test12.data.remote_data_source.shoes.UserBucketDto
import com.example.test12.data.remote_data_source.shoes.UserFavouriteDto
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.common.toShoes
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.flow

class ShoesUseCase (private val db: AppDatabase) {

    var userInfo:UserInfo? = null
    val repository = ShoesRepository()


    suspend fun getShoes() = flow<ResponseState> {
        return@flow try {
            emit(ResponseState.Loading())
            val result = repository.getShoes().map {
                it.toShoes()
            }
            userInfo?.let {
                repository.getFavourite(it.id).forEach{favour ->
                    result.find { it.id == favour.shoes_id }?.let { it.isFavourite = true }
                }
                repository.getBucket(it.id).forEach { bucket ->
                    result.find { it.id == bucket.shoes_id }?.let {
                        it.inBucket = true
                        it.count = bucket.shoes_count
                    }
                }
                emit(ResponseState.Success(result))
                return@flow
            }
            db.ShoesDao().getAllShoesInFavourite().forEach { favour ->
                result.find { it.id == favour.shoesId }?.let { it.isFavourite = true }
            }
            db.ShoesDao().getAllShoesInBucket().forEach { bucket ->
                result.find { it.id == bucket.shoesId }?.let {
                    it.inBucket = true
                    it.count = bucket.shoesCount
                }
            }
            if (db.ShoesDao().getAllShoes().isEmpty()) {
                db.ShoesDao().insertAll(
                    *result.map {
                        ShoesEntity(
                            shoesId = it.id,
                            shoesDescription = it.description,
                            shoesUrl = it.image,
                            shoesCost = it.cost.toFloat(),
                            shoesName = it.name,
                            shoesCount = it.count,
                            shoesInFavourite = it.isFavourite
                        )
                    }.toTypedArray()
                )
            }
            emit(ResponseState.Success(result))
        } catch (e:Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
    suspend fun getShoesByCategory(categoryId: Long) = flow<ResponseState> {
        return@flow try {
            emit(ResponseState.Loading())
            val result = repository.getShoesByCategory(categoryId).map {
                it.toShoes()
            }
            emit(ResponseState.Success(result))
        } catch (e: Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
    suspend fun getPopular() = flow<ResponseState> {
        return@flow try{
            emit(ResponseState.Loading())

            val result = repository.getPopular().map {
                it.toShoes()
            }
            emit(ResponseState.Success(result))
        } catch (e: Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
    suspend fun setFavourite(shoes_id: Long) = flow<ResponseState> {
        return@flow try{
            emit(ResponseState.Loading())
            userInfo?.let {
                repository.setFavourite(
                    UserFavouriteDto(
                        it.id, shoes_id
                    )
                )
                emit(ResponseState.Success(true))
                return@flow
            }
            db.ShoesDao().changeInFavourite(shoes_id)
            emit(ResponseState.Success(true))
        } catch (e: Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
    suspend fun setBucket(shoes_id: Long, shoes_count: Int) = flow<ResponseState> {
        return@flow try{
            emit(ResponseState.Loading())
            userInfo?.let {
                repository.setBucket(
                    UserBucketDto(
                        it.id, shoes_id, shoes_count
                    )
                )
                emit(ResponseState.Success(true))
                return@flow
            }
            db.ShoesDao().changeInBucket(shoes_id, shoes_count)
            emit(ResponseState.Success(true))
        } catch (e: Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
    suspend fun removeFavourite(shoes_id: Long)= flow<ResponseState>{
        return@flow try{
            emit(ResponseState.Loading())
            userInfo?.let {
                repository.removeFavourite(
                    UserFavouriteDto(
                        it.id, shoes_id
                    )
                )
                emit(ResponseState.Success(true))
                return@flow
            }
            db.ShoesDao().changeInFavourite(shoes_id)
            emit(ResponseState.Success(true))
        } catch (e: Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
    suspend fun removeBucket(shoes_id: Long, shoes_count: Int)= flow<ResponseState>{
        return@flow try{
            emit(ResponseState.Loading())
            userInfo?.let {
                repository.removeBucket(
                    UserBucketDto(
                        it.id, shoes_id, shoes_count
                    )
                )
                emit(ResponseState.Success(true))
                return@flow
            }
            db.ShoesDao().changeInBucket(shoes_id, shoes_count)
            emit(ResponseState.Success(true))
        } catch (e: Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
    suspend fun changeCount(shoes_id: Long, shoes_count: Int) = flow<ResponseState> {
        return@flow try {
            emit(ResponseState.Loading())
            userInfo?.let {
                repository.changeCount(
                    UserBucketDto(
                        it.id, shoes_id, shoes_count
                    )
                )
                emit(ResponseState.Success(true))
                return@flow
            }
            db.ShoesDao().changeInBucket(shoes_id, shoes_count)
            emit(ResponseState.Success(true))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }
}