package com.example.test12.presentation.profile

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.domain.sign_in.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel: ScreenModel {
    val state = MutableStateFlow(ProfileScreenState())
    val userUseCase = UserUseCase()

    init {
        screenModelScope.launch {
            val result = userUseCase.getProfile()
            state.update {
                it.copy(
                    name = result?.userMetadata?.get("user_name").toString(),
                )
            }
        }
    }
    fun editProfile(){
        state.update {
            it.copy(editState = !state.value.editState)
        }
    }
    fun choosePicture(){
        state.update {
            it.copy(choosePicture = !state.value.choosePicture)
        }
    }
}