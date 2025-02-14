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
import com.example.test12.domain.sign_in.UserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val db: AppDatabase): ScreenModel {
    val state = MutableStateFlow(HomeScreenState())
    val categoryUseCase = CategoryUseCase()
    val salesUseCase = SalesUseCase()
    val shoesList = mutableStateListOf<Shoes>()
    val userUseCase = UserUseCase()
    val shoesUseCase = ShoesUseCase(db)

    init{
        screenModelScope.launch {
            shoesUseCase.userInfo = userUseCase.getProfile()
            updateData()
        }
    }


    fun updateData() {
        getAllShoes()
        getAllCategory()
        getPopularShoes()
        getAllSales()
    }



    fun inFavourite(shoes: Shoes){
        screenModelScope.launch(Dispatchers.IO) {
            val changeRecord = shoesList.indexOf(shoes)
            if (shoes.isFavourite) {
                shoesUseCase.removeFavourite(shoes.id).collect{}
            }
            else {
                shoesUseCase.setFavourite(shoes.id).collect{}
            }
            shoesList[changeRecord] = shoesList[changeRecord].copy(isFavourite = !shoes.isFavourite)
        }
    }
    fun inBucket(shoes: Shoes){
        screenModelScope.launch(Dispatchers.IO) {
            val changeRecord = shoesList.indexOf(shoes)
            if (shoes.inBucket) {
                shoesUseCase.removeBucket(shoes.id, shoes_count = 0).collect{}
            }
            else {
                shoesUseCase.setBucket(shoes.id, shoes_count = 1).collect{}
            }
            shoesList[changeRecord] = shoesList[changeRecord].copy(inBucket = !shoes.inBucket, count = shoes.count)
        }
    }

    private fun getAllCategory() {
        screenModelScope.launch {
            val result = categoryUseCase.getAllCategory()
            result.collect { response ->
                when (response) {
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
                    is ResponseState.Success<*> -> {
                        state.update {
                            it.copy(category = response.data as List<Category>, isLoading = false)
                        }
                    }
                }
            }
        }
    }
    private fun getAllSales(){
        screenModelScope.launch {
            val result = salesUseCase.getSales()
            result.collect { response ->
                when (response) {
                    is ResponseState.Success<*> -> {
                        state.update {
                            it.copy(sales = response.data as List<Sales>, isLoading = false)
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
    private fun getPopularShoes() {
        screenModelScope.launch(Dispatchers.IO) {
            val result = shoesUseCase.getPopular()
            result.collect { response ->
                when (response) {
                    is ResponseState.Success<*> -> {
                        shoesList.clear()
                        shoesList.addAll(response.data as List<Shoes>)
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
    private fun getAllShoes() {
        screenModelScope.launch(Dispatchers.IO) {
            val result = shoesUseCase.getShoes()
            result.collect { response ->
                when (response) {
                    is ResponseState.Success<*> -> {
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
    fun signOut(){
        screenModelScope.launch {
            val result = userUseCase.signOut()
            result.collect{response ->
                when (response) {
                    is ResponseState.Success<*> -> {
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
}