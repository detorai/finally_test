package com.example.test12.presentation.details

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.domain.category.Category
import com.example.test12.domain.category.CategoryUseCase
import com.example.test12.domain.category.CategoryWithShoes
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.shoes.Shoes
import com.example.test12.domain.shoes.ShoesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(private val db: AppDatabase, private val selectedShoes: Shoes): ScreenModel {
    val shoesList = mutableStateListOf<Shoes>()
    val categoryUseCase = CategoryUseCase()
    val shoesUseCase = ShoesUseCase(db)

    init {
        getAllShoes()
        getCategoryById(selectedShoes.id)
    }

    private fun getCategoryById(id: Long){
        screenModelScope.launch {
            val result = categoryUseCase.getCategoryById(id)
            result.collect { response ->
                when (response) {
                    is ResponseState.Error -> {}
                    is ResponseState.Success<*> -> {
                        shoesList.apply {
                            clear()
                            addAll(
                                (response.data as CategoryWithShoes).shoes
                            )
                        }
                    }
                    is ResponseState.Loading -> {}
                }
            }
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
                        shoesList.addAll(response.data as List<Shoes>)
                    }

                    is ResponseState.Loading -> {}
                }
            }
        }
    }
}