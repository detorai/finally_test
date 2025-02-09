package com.example.test12.presentation.secondary_screen

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

class SecondaryViewModel(type: ScreenType, categoryScreen: Category? = null, db: AppDatabase): ScreenModel {

    val state = MutableStateFlow(SecondaryScreenState(currentScreen = type))
    val categoryUseCase = CategoryUseCase()
    val shoesUseCase = ShoesUseCase(db)
    val shoesList = mutableStateListOf<Shoes>()
    val userUseCase = UserUseCase()

    init {
        screenModelScope.launch {
            shoesUseCase.userInfo = userUseCase.getProfile()
            when (type){
                ScreenType.POPULAR -> {
                    state.update {
                        it.copy(label = "Популярное", currentScreen = ScreenType.POPULAR)
                    }
                    getShoesByCategoryId(6)
                }
                ScreenType.CATEGORY -> {
                    getAllCategory()
                }
                ScreenType.FAVOURITE -> {
                    state.update {
                        it.copy(label = "Избранное", currentScreen = ScreenType.FAVOURITE)
                    }
                    getFavouriteShoes()
                }
            }
            categoryScreen?.let { cat ->
                selectedCategory(cat)
                state.update {
                    it.copy(
                        label = cat.name,
                        selectedCategory = categoryScreen,
                    )
                }
                getShoesByCategoryId(cat.id)
            }
        }
    }


    fun updateScreen(newScreen: ScreenType){
        if (state.value.currentScreen != newScreen){
            when (newScreen) {
                ScreenType.POPULAR -> {
                    state.update {
                        it.copy(label = "Популярное", currentScreen = ScreenType.POPULAR)
                    }
                    getShoesByCategoryId(6)
                }
                ScreenType.CATEGORY -> {
                    getAllCategory()
                }
                ScreenType.FAVOURITE -> {
                    state.update {
                        it.copy(label = "Избранное", currentScreen = ScreenType.FAVOURITE)
                    }
                    getFavouriteShoes()
                }
            }
        }
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

    fun selectedCategory(category: Category){
        state.update {
            it.copy(
                selectedCategory = category,
                label = category.name,
            )
        }
        getShoesByCategoryId(
            category.id
        )
    }
    private fun getShoesByCategoryId(categoryId: Long){
        screenModelScope.launch {
            val result = shoesUseCase.getShoesByCategory(categoryId)
            result.collect { response ->
                when (response) {
                    is ResponseState.Success<*> -> {
                        shoesList.clear()
                        shoesList.addAll((response.data as List<Shoes>).filter { it.category == categoryId })
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


    private fun getAllCategory() {
        screenModelScope.launch {
            val result = categoryUseCase.getAllCategory()
            result.collect { response ->
                when (response) {
                    is ResponseState.Success<*> -> {
                        state.update {
                            it.copy(category = response.data as List<Category>, isLoading = false)
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
    private fun getFavouriteShoes() {
        screenModelScope.launch {
            val result = shoesUseCase.getShoes()
            result.collect { response ->
                when (response) {
                    is ResponseState.Success<*> -> {
                        shoesList.clear()
                        shoesList.addAll((response.data as List<Shoes>).filter { it.isFavourite })
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