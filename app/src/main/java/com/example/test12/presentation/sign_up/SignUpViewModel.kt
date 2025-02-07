package com.example.test12.presentation.sign_up

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.sign_in.SignInUseCase
import com.example.test12.domain.sign_in.request.AuthRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel: ScreenModel {
    val state = MutableStateFlow(SignUpScreenState())
    val signUpUseCase = SignInUseCase()


    fun onName(name: String){
        state.update {
            it.copy(name = name)
        }
    }
    fun onEmail(email: String){
        state.update {
            it.copy(email = email)
        }
    }
    fun onPassword(password: String){
        state.update {
            it.copy(password = password)
        }
    }
    fun onPasswordVis(){
        state.update {
            it.copy(passwordVisible = !it.passwordVisible)
        }
    }
    fun onCheck(){
        state.update {
            it.copy(checkState = !it.checkState)
        }
    }
    fun resetError(){
        state.update {
            it.copy(error = null)
        }
    }
    fun checkEmailPattern(email: String): Boolean{
        val pattern = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$"
        val regex = Regex(pattern)

        if (!regex.matches(email)){
            state.update {
                it.copy(error = "Incorrect Email")
            }
            return false
        }
        return true
    }


    fun signUp(email: String, password: String){
        val authRequest = AuthRequest(
            email, password
        )
        if (!checkEmailPattern(email)) return
        if (!state.value.checkState) return
        screenModelScope.launch {
            val result = signUpUseCase.signUp(authRequest)
            result.collect{response ->
                when(response) {
                    is ResponseState.Error -> {
                        state.update {
                            it.copy(error = "Sign Up is fail")
                        }
                    }
                    is ResponseState.Loading -> {
                        state.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is ResponseState.Success<*> -> {
                        state.update {
                            it.copy(isSignUp = true)
                        }
                    }
                }
            }
        }
    }
}