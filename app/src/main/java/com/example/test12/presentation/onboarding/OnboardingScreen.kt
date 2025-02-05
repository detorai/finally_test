package com.example.test12.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import com.example.test12.R

class OnboardingScreen: Screen {
    @Composable
    override fun Content() {
        Onboarding()
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Onboarding(){
        val pagerState = rememberPagerState (pageCount = {3} )
        var visible by remember { mutableStateOf(true) }
        val coroutineScope = rememberCoroutineScope()

        val pagerImage = listOf("", R.drawable.image_2, R.drawable.image_3)
        val pagerLabel = listOf("", "Начнем\nпутешествие", "Умная, великолепная и модная\nколлекция Изучите сейчас")
        val pagerDesc = listOf("", R.drawable.image_2, R.drawable.image_3)

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.isScrollInProgress }.collect{ page ->
                visible = !page
            }
        }

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF48B2E7),
                        Color(0xFF44A9DC),
                        Color(0xFF2B6B8B)
                    )
                )
            )
        ){
            HorizontalPager(
                state = pagerState
            ) { }
        }

    }
}