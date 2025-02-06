package com.example.test12.domain.shoes

import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.data.local_data_source.ShoesEntity
import com.example.test12.data.remote_data_source.shoes.ShoesRepository
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.common.toShoes
import kotlinx.coroutines.flow.flow

class ShoesUseCase (private val db: AppDatabase) {
    val repository = ShoesRepository()


    suspend fun getAllShoes() = flow<ResponseState>{
        return@flow try{
            emit(ResponseState.Loading())
            val result = repository.getShoes().map{
                it.toShoes()
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
            emit(ResponseState.Success(data = result))
        } catch (e:Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
    suspend fun inFavourite(shoes: Shoes){
        db.ShoesDao().changeInFavourite(shoesId = shoes.id)
    }
    suspend fun inBucket(shoes: Shoes){
        db.ShoesDao().changeInBucket(shoesId = shoes.id, count = 1)
    }
    suspend fun countPlus(shoes: Shoes){
        db.ShoesDao().changeInBucket(shoesId = shoes.id, count = shoes.count + 1)
    }
    suspend fun countMinus(shoes: Shoes){
        if (shoes.count <= 1) {
            db.ShoesDao().changeInBucket(shoesId = shoes.id, count = 0)
        } else {
            db.ShoesDao().changeInBucket(shoesId = shoes.id, count = shoes.count - 1)
        }
    }
    suspend fun deleteFromBucket(shoes: Shoes){
        db.ShoesDao().changeInBucket(shoesId = shoes.id, count = 0)
    }

}