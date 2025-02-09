package com.example.test12.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.test12.R
import com.example.test12.presentation.secondary_screen.ScreenType
import com.example.test12.presentation.ui.theme.TextColor


@Composable
fun CommonTopBar(
    onBack: ()-> Unit,
    label: String,
    onFavourite: ()-> Unit = {},
    screenType: ScreenType? = null
){
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CommonButtonWithIcon(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterStart),
                    icon = R.drawable.arrow_back
                )
                Text(
                    label,
                    color = TextColor,
                )
                if (screenType == ScreenType.POPULAR || screenType ==ScreenType.FAVOURITE )
                    CommonButtonWithIcon(
                        onClick = onFavourite,
                        modifier = Modifier.align(Alignment.CenterEnd),
                        icon = if (screenType == ScreenType.FAVOURITE) R.drawable.heart_fill else R.drawable.heart
                    )
            }
        }

    }
}