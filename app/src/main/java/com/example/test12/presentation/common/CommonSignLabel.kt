package com.example.test12.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.test12.presentation.ui.theme.Typography

@Composable
fun CommonSignLabel(
    label: String,
    desc: String,
    modifier: Modifier
    ){
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            label,
            style = Typography.titleLarge
        )
        Text(
            desc,
            modifier = Modifier.padding(top = 8.dp),
            style = Typography.titleSmall
        )
    }
}