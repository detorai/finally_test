package com.example.test12.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test12.R
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.presentation.ui.theme.Accent
import com.example.test12.presentation.ui.theme.Block
import kotlinx.coroutines.delay

data class SplashScreen(
    private val db: AppDatabase
): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel { OnboardingViewModel(db) }
        Splash(navigator, db, viewModel)
    }
    @Composable
    fun Splash(navigator: Navigator, db: AppDatabase, viewModel: OnboardingViewModel){

        LaunchedEffect(Unit){
            delay(3000)
            navigator.push(OnboardingScreen(db))
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().background(Accent)
        ){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(129.dp).background(Block, RoundedCornerShape(120.dp) )
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.bag_splash),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
        }
    }
}