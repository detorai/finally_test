package com.example.test12.presentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.test12.presentation.ui.theme.Background

@Composable
fun CommonScaffold(
    content:  @Composable (PaddingValues) -> Unit = {},
    topBar:  @Composable () -> Unit = {},
    bottomBar:  @Composable () -> Unit = {}
){
    Scaffold(
        topBar = topBar,
        containerColor = Background,
        bottomBar = bottomBar,
        content = {padding ->
            content(padding)
        }
    )
}