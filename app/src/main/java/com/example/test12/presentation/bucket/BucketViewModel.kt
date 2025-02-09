package com.example.test12.presentation.bucket

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.shoes.Shoes
import com.example.test12.domain.shoes.ShoesUseCase
import com.example.test12.domain.sign_in.UserUseCase
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BucketViewModel(db: AppDatabase) : ScreenModel{
    val state = MutableStateFlow(BucketScreenState())
    val shoesUseCase = ShoesUseCase(db)
    val shoesList = mutableStateListOf<Shoes>()
    val delivery = mutableDoubleStateOf(123.00)
    val userUseCase = UserUseCase()

    init {
        screenModelScope.launch {
            shoesUseCase.userInfo = userUseCase.getProfile()
            getBucketShoes()
        }
    }

    fun countPlus(shoes: Shoes){
        screenModelScope.launch(Dispatchers.IO) {
            val changeRecord = shoesList.indexOf(shoes)
            shoesUseCase.changeCount(shoes.id, shoes.count + 1).collect{}
            shoesList[changeRecord] = shoesList[changeRecord].copy(count = shoes.count + 1)
        }
    }
    fun countMinus(shoes: Shoes){
        screenModelScope.launch(Dispatchers.IO) {
            val changeRecord = shoesList.indexOf(shoes)
            shoesUseCase.changeCount(shoes.id, shoes.count - 1).collect{}
            if (shoes.count <= 1) {
                shoesList.removeAt(changeRecord)
                shoesUseCase.removeBucket(shoes.id, shoes.count)
                return@launch
            }
            shoesList[changeRecord] = shoesList[changeRecord].copy(count = shoes.count - 1)
        }
    }
    fun deleteFromBucket(shoes: Shoes){
        screenModelScope.launch(Dispatchers.IO) {
            val changeRecord = shoesList.indexOf(shoes)
            shoesUseCase.removeBucket(shoes.id, shoes.count).collect{}
            shoesList.removeAt(changeRecord)

        }
    }

    private fun getBucketShoes() {
        screenModelScope.launch {
            val result = shoesUseCase.getShoes()
            result.collect { response ->
                when (response) {
                    is ResponseState.Success<*> -> {
                        shoesList.clear()
                        shoesList.addAll((response.data as List<Shoes>).filter { it.inBucket })
                        state.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is ResponseState.Error -> {
                        state.update {
                            it.copy(error = response.error, isLoading = false)
                        }
                    }
                    is ResponseState.Loading -> {
                        state.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }
    fun resetError(){
        state.update {
            it.copy(error = null)
        }
    }
}