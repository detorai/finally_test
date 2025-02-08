package com.example.test12.presentation.bucket

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.shoes.Shoes
import com.example.test12.domain.shoes.ShoesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BucketViewModel(db: AppDatabase) : ScreenModel{

    val shoesUseCase = ShoesUseCase(db)
    val shoesList = mutableStateListOf<Shoes>()
    val delivery = mutableDoubleStateOf(123.00)

    init {
        getAllShoes()

    }

    fun countPlus(shoes: Shoes){
        screenModelScope.launch(Dispatchers.IO) {
            val changeRecord = shoesList.indexOf(shoes)
            shoesUseCase.countPlus(shoes)
            shoesList[changeRecord] = shoesList[changeRecord].copy(count = shoes.count + 1)
        }
    }
    fun countMinus(shoes: Shoes){
        screenModelScope.launch(Dispatchers.IO) {
            val changeRecord = shoesList.indexOf(shoes)
            shoesUseCase.countMinus(shoes)
            if (shoes.count <= 1) {
                shoesList.removeAt(changeRecord)
                return@launch
            }
            shoesList[changeRecord] = shoesList[changeRecord].copy(count = shoes.count - 1)
        }
    }
    fun deleteFromBucket(shoes: Shoes){
        screenModelScope.launch(Dispatchers.IO) {
            val changeRecord = shoesList.indexOf(shoes)
            shoesUseCase.deleteFromBucket(shoes)
            shoesList.removeAt(changeRecord)

        }
    }

    private fun getAllShoes() {
        screenModelScope.launch {
            val result = shoesUseCase.getAllShoes()
            result.collect { response ->
                when (response) {
                    is ResponseState.Error -> {}
                    is ResponseState.Success<*> -> {
                        shoesList.clear()
                        shoesList.addAll((response.data as List<Shoes>).filter { it.inBucket })
                    }
                    is ResponseState.Loading -> {}
                }
            }
        }
    }

}