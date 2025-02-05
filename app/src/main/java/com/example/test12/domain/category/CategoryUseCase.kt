package com.example.test12.domain.category

import com.example.test12.data.remote_data_source.category.CategoryRepository
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.common.toShoes
import kotlinx.coroutines.flow.flow

class CategoryUseCase {
    val repository = CategoryRepository()

    suspend fun getAllCategory() = flow<ResponseState> {
        return@flow try{
            emit(ResponseState.Loading())
            val result = repository.getAllCategory().map {
                Category(
                    id = it.id,
                    name = it.category_name
                )
            }
            emit(ResponseState.Success(data = result))
        } catch (e:Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
    suspend fun getCategoryById(id: Long) = flow<ResponseState> {
        return@flow try {
            emit(ResponseState.Loading())
            val result = repository.getCategoryById(id).map {
                it.Shoes.toShoes()
            }
            val category = CategoryWithShoes(
                cat_id = id,
                shoes = result
            )
            emit(ResponseState.Success(data = category))
        } catch (e:Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
}