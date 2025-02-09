package com.example.test12.presentation.sign_up

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.sign_in.UserUseCase
import com.example.test12.domain.sign_in.request.AuthRequest
import com.example.test12.domain.sign_in.request.RegisterRequest
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel: ScreenModel {
    val state = MutableStateFlow(SignUpScreenState())
    val userUseCase = UserUseCase()


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
    private fun checkEmailPattern(email: String): Boolean{
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


    fun signUp(email: String, password: String, name: String){
        val registerRequest = RegisterRequest(
            email, password, name
        )
        if (!checkEmailPattern(email)) return
        if (!state.value.checkState) return
        screenModelScope.launch {
            val result = userUseCase.signUp(registerRequest)
            result.collect{response ->
                when(response) {
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
                            it.copy(isSignUp = true)
                        }
                    }
                }
            }
        }
    }
}