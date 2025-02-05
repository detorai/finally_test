package com.example.test12.presentation.sign_in

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.sign_in.SignInUseCase
import com.example.test12.domain.sign_in.request.AuthRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel: ScreenModel {

    val state = MutableStateFlow(SignInScreenState())
    val singInUseCase = SignInUseCase()

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
    fun resetError(){
        state.update {
            it.copy(Error = null)
        }
    }
    fun checkEmailEmpty(email: String): Boolean{
        if (email.isEmpty()){
            state.update {
                it.copy(Error = "Email is empty")
            }
            return false
        }
        return true
    }
    fun checkPasswordEmpty(password: String): Boolean{
        if (password.isEmpty()){
            state.update {
                it.copy(Error = "Password is empty")
            }
            return false
        }
        return true
    }
    fun checkEmailPattern(email: String): Boolean{
        val pattern = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{3,}$"
        val regex = Regex(pattern)

        if (!regex.matches(email)){
            state.update {
                it.copy(Error = "Incorrect Email")
            }
            return false
        }
        return true
    }

    fun auth(email: String, password: String){
        val authRequest = AuthRequest(
            email, password
        )
        if (!checkEmailEmpty(authRequest.email)) return
        if (!checkPasswordEmpty(authRequest.password)) return
        if (!checkEmailEmpty(authRequest.email)) return

        screenModelScope.launch {
            val result = singInUseCase.auth(authRequest)
            result.collect{response ->
                when(response) {
                    is ResponseState.Error -> {
                        state.update {
                            it.copy(Error = "Not Auth")
                        }
                    }
                    is ResponseState.Loading -> {
                        state.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is ResponseState.Success<*> -> {
                        state.update {
                            it.copy(isSignIn = true)
                        }
                    }
                }
            }
        }
    }
}