package com.example.test12

import com.example.test12.presentation.sign_in.SignInViewModel
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    val viewModel = SignInViewModel()

    @Test
    fun checkEmailEmpty(){
        viewModel.checkEmailEmpty("123@123.com")
    }
    @Test
    fun checkPasswordEmpty(){
        viewModel.checkPasswordEmpty("123")
    }
    @Test
    fun checkEmailPattern(){
        viewModel.checkEmailPattern("123@123.com")
    }
}