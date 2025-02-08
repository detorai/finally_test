package com.example.test12.presentation.change_password

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.sign_in.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel: ScreenModel {
    val state = MutableStateFlow(ForgotPasswordScreenState())
    val forgotPassUseCase = SignInUseCase()

    fun onEmail(email: String){
        state.update {
            it.copy(email = email)
        }
    }
    fun resetError(){
        state.update {
            it.copy(error = null)
        }
    }

    fun resetPassword(email: String){
        screenModelScope.launch {
            val result = forgotPassUseCase.resetPassword(email)
            result.collect{response ->
                when(response) {
                    is ResponseState.Error -> {
                        state.update {
                            it.copy(error = "")
                        }
                    }
                    is ResponseState.Loading -> {
                        state.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is ResponseState.Success<*> -> {
                        state.update {
                            it.copy(success = true)
                        }
                    }
                }
            }
        }
    }

}