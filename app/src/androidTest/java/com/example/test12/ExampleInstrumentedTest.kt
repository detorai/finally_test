package com.example.test12

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.test12.presentation.sign_in.SignInScreen
import com.example.test12.presentation.sign_in.SignInScreenState
import com.example.test12.presentation.sign_in.SignInViewModel

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//@RunWith(AndroidJUnit4::class)
//class ExampleInstrumentedTest {
//
//    private val viewModel = SignInViewModel()
//
//    @get: Rule
//    val compose = createComposeRule()
//
//    @Test
//    fun checkEmailEmptyError(){
//        viewModel.auth("", "")
//        compose.setContent {
//            SignInScreen().SignIn(viewModel)
//        }
//        compose.onNodeWithText("Email is empty").assertIsDisplayed()
//    }
//
//    @Test
//    fun checkPasswordEmptyError(){
//        viewModel.auth("123@123.com", "")
//        compose.setContent {
//            SignInScreen().SignIn(viewModel)
//        }
//        compose.onNodeWithText("Password is empty").assertIsDisplayed()
//    }
//}