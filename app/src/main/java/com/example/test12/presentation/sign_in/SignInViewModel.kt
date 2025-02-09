package com.example.test12.presentation.sign_in

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.sign_in.UserUseCase
import com.example.test12.domain.sign_in.request.AuthRequest
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel: ScreenModel {
    val state = MutableStateFlow(SignInScreenState())
    val userUseCase = UserUseCase()

    init {
        screenModelScope.launch(Dispatchers.IO) {
            userUseCase.userAuth.sessionStatus.collect{status ->
                if(status is SessionStatus.Authenticated) {
                    state.update {
                        it.copy(isSignIn = true, isLoading = false)
                    }
                }
            }
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
    fun resetError(){
        state.update {
            it.copy(error = null)
        }
    }
    fun checkEmailEmpty(email: String): Boolean{
        if (email.isEmpty()){
            state.update {
                it.copy(error = "Email is empty")
            }
            return false
        }
        return true
    }
    fun checkPasswordEmpty(password: String): Boolean{
        if (password.isEmpty()){
            state.update {
                it.copy(error = "Password is empty")
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
                it.copy(error = "Incorrect Email")
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
        if (!checkEmailPattern(authRequest.email)) return

        screenModelScope.launch {
            val result = userUseCase.auth(authRequest)
            result.collect{response ->
                when (response) {
                    is ResponseState.Success<*> -> {
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