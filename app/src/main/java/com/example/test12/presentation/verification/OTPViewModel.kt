package com.example.test12.presentation.verification

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.sign_in.UserUseCase
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OTPViewModel(private val email: String): ScreenModel {
    val state = MutableStateFlow(OTPScreenState())
    val userUseCase = UserUseCase()


    fun onOTP(code: String){
        state.update {
            it.copy(otpCode = code)
        }
        if (state.value.otpCode.length == 6) {
            sendOTP(email, code)
        }
    }
    fun onNewPass(password: String){
        state.update {
            it.copy(newPassword = password)
        }
    }
    fun generatePassword(password: String){
        val map: Map<Char, Char> = mapOf(
            Pair('o', '0'),
            Pair('a', '@'),
            Pair('i', '1'),
            Pair('s', '5'),
            Pair('g', '9'),
            Pair('n', '№')
        )
        val result = password.map { if(map.containsKey(it)) map[it] else it}
        state.update {
            it.copy(newPassword = result.joinToString(""), passwordState = true)
        }

    }
    private fun sendOTP(email: String, token: String){
        screenModelScope.launch {
            val result = userUseCase.sendOTP(email, token)
            result.collect{response ->
                when(response){
                    is ResponseState.Error -> {
                        state.update {
                            it.copy(dialog = response.error, isLoading = false)
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
    fun updateUser(password: String){
        screenModelScope.launch {
            val result = userUseCase.updateUser(password)
            result.collect{response ->
                when(response){
                    is ResponseState.Error -> {
                        state.update {
                            it.copy(dialog = response.error, isLoading = false)
                        }
                    }
                    is ResponseState.Loading -> {
                        state.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is ResponseState.Success<*> -> {

                    }
                }
            }
        }
    }
}