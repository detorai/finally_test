package com.example.test12.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import cafe.adriel.voyager.navigator.Navigator
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.presentation.home.HomeScreen
import com.example.test12.presentation.onboarding.OnboardingScreen
import com.example.test12.presentation.onboarding.SplashScreen
import com.example.test12.presentation.sign_in.SignInScreen
import com.example.test12.presentation.sign_up.SignUpScreen
import com.example.test12.presentation.ui.theme.Test12Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MatuleDatabase3"
        ).build()
        setContent {
            Test12Theme {
                Navigator(SignInScreen(db))
            }
        }
    }
}

