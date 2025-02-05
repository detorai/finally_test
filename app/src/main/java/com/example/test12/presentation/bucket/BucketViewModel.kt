package com.example.test12.presentation.bucket

import androidx.compose.runtime.mutableStateListOf
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
    val state = MutableStateFlow(BucketScreenState())
    val shoesUseCase = ShoesUseCase(db)
    val shoesList = mutableStateListOf<Shoes>()

    init {
        getAllShoes()
        getCount()
    }


    private fun getCount(){
        state.update {
            it.copy(count = shoesList.size)
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