package com.example.test12.presentation.details

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.domain.category.Category
import com.example.test12.domain.category.CategoryUseCase
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.shoes.Shoes
import com.example.test12.domain.shoes.ShoesUseCase
import com.example.test12.domain.sign_in.UserUseCase
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(private val db: AppDatabase, private val selectedShoes: Shoes): ScreenModel {

    val state = MutableStateFlow(DetailsScreenState())
    val shoesList = mutableStateListOf<Shoes>()
    val shoesUseCase = ShoesUseCase(db)
    val categoryUseCase = CategoryUseCase()

    init {
        updateScreen(selectedShoes)
        getAllShoes()
        getCategoryById(selectedShoes.id)
    }
    fun resetError(){
        state.update {
            it.copy(error = null)
        }
    }
    fun updateScreen(shoes: Shoes){
        state.update {
            it.copy(selectedShoesState = shoes)
        }
    }
    fun inFavourite(shoes: Shoes){
        screenModelScope.launch(Dispatchers.IO) {
            val changeRecord = shoesList.indexOf(shoes)
            if (shoes.isFavourite) {
                shoesUseCase.setFavourite(shoes.id).collect{}
            }
            else {
                shoesUseCase.removeFavourite(shoes.id).collect{}
            }
            shoesList[changeRecord] = shoesList[changeRecord].copy(isFavourite = !shoes.isFavourite)
        }
    }
    fun inBucket(shoes: Shoes){
        screenModelScope.launch(Dispatchers.IO) {
            val changeRecord = shoesList.indexOf(shoes)
            if (shoes.inBucket) {
                shoesUseCase.setBucket(shoes.id, shoes_count = 1).collect{}
            }
            else {
                shoesUseCase.removeBucket(shoes.id, shoes_count = 0).collect{}
            }
            shoesList[changeRecord] = shoesList[changeRecord].copy(inBucket = !shoes.inBucket, count = shoes.count)
        }
    }
    fun getCategoryById(id: Long){
        screenModelScope.launch {
            val result = categoryUseCase.getAllCategory()
            result.collect{response ->
                when (response) {
                    is ResponseState.Success<*> -> {
                        state.update {
                            it.copy(success = true, isLoading = false, category = (response.data as List<Category>).find{ it.id == id }!!.name)
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
        screenModelScope.launch {
            val result = shoesUseCase.getShoes()
            result.collect { response ->
                when (response) {
                    is ResponseState.Success<*> -> {
                        shoesList.clear()
                        shoesList.addAll(response.data as List<Shoes>)
                        state.update {
                            it.copy(success = true, isLoading = false)
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