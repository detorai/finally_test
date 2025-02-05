package com.example.test12.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.example.test12.presentation.ui.theme.Background

class HomeScreen: Screen {
    @Composable
    override fun Content() {
        Home()
    }
    @Composable
    fun Home(){
        Column(
            modifier = Modifier.fillMaxSize().background(Background)
        ) {  }
    }
}