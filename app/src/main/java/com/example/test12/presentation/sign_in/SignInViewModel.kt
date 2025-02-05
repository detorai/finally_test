package com.example.test12.presentation.sign_in

import cafe.adriel.voyager.core.model.ScreenModel
import kotlinx.coroutines.flow.MutableStateFlow

class SignInViewModel: ScreenModel {

    val state = MutableStateFlow(SignInScreenState())

    fun onEmail(email: String){
        throw
        NotImplementedError()
    }
    fun onPassword(password: String){
        throw
        NotImplementedError()
    }
    fun resetError(){
        throw
        NotImplementedError()
    }
    fun checkEmailEmpty(email: String){
        throw
        NotImplementedError()
    }
    fun checkPasswordEmpty(password: String){
        throw
        NotImplementedError()
    }
    fun checkEmailPattern(email: String){
        throw
        NotImplementedError()
    }

    fun auth(email: String, password: String){
        throw
        NotImplementedError()
    }
}