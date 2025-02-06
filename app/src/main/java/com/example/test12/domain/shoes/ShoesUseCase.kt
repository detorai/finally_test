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

    suspend fun getAllShoesLocal(){
        db.ShoesDao().getAllShoes()
    }

    suspend fun getAllFavourite() {
        db.ShoesDao().getAllShoesInFavourite()
    }
    suspend fun getAllBucket() {
        db.ShoesDao().getAllShoesInFavourite()
    }
    suspend fun inFavourite(shoes: Shoes){
        db.ShoesDao().changeInFavourite(shoesId = shoes.id)
    }
}