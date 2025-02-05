package com.example.test12.presentation.home

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.domain.category.Category
import com.example.test12.domain.category.CategoryUseCase
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.sales.Sales
import com.example.test12.domain.sales.SalesUseCase
import com.example.test12.domain.shoes.Shoes
import com.example.test12.domain.shoes.ShoesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val db: AppDatabase): ScreenModel {
    val state = MutableStateFlow(HomeScreenState())
    val shoesUseCase = ShoesUseCase(db)
    val categoryUseCase = CategoryUseCase()
    val salesUseCase = SalesUseCase()
    val shoesList = mutableStateListOf<Shoes>()

    init{
        updateData()
    }

    fun updateData() {
        getAllCategory()
        getAllShoes()
        getAllSales()
    }

    private fun getAllCategory() {
        screenModelScope.launch {
            val result = categoryUseCase.getAllCategory()
            result.collect { response ->
                when (response) {
                    is ResponseState.Error -> {}
                    is ResponseState.Success<*> -> {
                        state.update {
                            it.copy(category = response.data as List<Category>)
                        }
                    }
                    is ResponseState.Loading -> {}
                }
            }
        }
    }
    private fun getAllSales(){
        screenModelScope.launch {
            val result = salesUseCase.getSales()
            result.collect { response ->
                when (response) {
                    is ResponseState.Error -> {}
                    is ResponseState.Success<*> -> {
                        state.update {
                            it.copy(sales = response.data as List<Sales>)
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
    fun inFavourite(shoes: Shoes){
        screenModelScope.launch(Dispatchers.IO) {
            val changeRecord = shoesList.indexOf(shoes)
            shoesUseCase.inFavourite(shoes)
            shoesList[changeRecord] = shoesList[changeRecord].copy(isFavourite = !shoes.isFavourite)
        }
    }
}