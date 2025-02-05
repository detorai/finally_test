package com.example.test12.presentation.secondary_sreen

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SecondaryViewModel(type: ScreenType, categoryScreen: Category? = null, db: AppDatabase): ScreenModel {
    val categoryUseCase = CategoryUseCase()
    val shoesUseCase = ShoesUseCase(db)
    val shoesList = mutableStateListOf<Shoes>()
    val state = MutableStateFlow(SecondaryScreenState(currentScreen = type))

    init {
        when (type){
            ScreenType.POPULAR -> {
                state.update {
                    it.copy(label = "Популярное", currentScreen = ScreenType.POPULAR)
                }
                getCategoryById(6)
            }
            ScreenType.CATEGORY -> {
                getAllCategory()
            }
            ScreenType.FAVOURITE -> {
                state.update {
                    it.copy(label = "Избранное", currentScreen = ScreenType.FAVOURITE)
                }
                getAllShoes()
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
        }
    }


    fun updateScreen(newScreen: ScreenType){
        if (state.value.currentScreen != newScreen){
            when (newScreen) {
                ScreenType.POPULAR -> {
                    state.update {
                        it.copy(label = "Популярное", currentScreen = ScreenType.POPULAR)
                    }
                    getCategoryById(6)
                }
                ScreenType.CATEGORY -> {
                    getAllCategory()
                }
                ScreenType.FAVOURITE -> {
                    state.update {
                        it.copy(label = "Избранное", currentScreen = ScreenType.FAVOURITE)
                    }
                    getAllShoes()
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



    fun selectedCategory(category: Category){
        state.update {
            it.copy(
                selectedCategory = category,
                label = category.name,
            )
        }
        getCategoryById(
            category.id
        )
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
                        shoesList.addAll((response.data as List<Shoes>).filter { it.isFavourite })
                    }

                    is ResponseState.Loading -> {}
                }
            }
        }
    }
}