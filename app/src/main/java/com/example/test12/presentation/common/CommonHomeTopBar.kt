package com.example.test12.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test12.R
import com.example.test12.presentation.ui.theme.TextColor

@Composable
fun CommonHomeTopBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 48.dp, start = 20.dp, end = 20.dp)
    ) {
        IconButton(
            onClick = { }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.hamburger),
                contentDescription = "",
                tint = Color.Unspecified
            )
        }
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier.width(142.dp).height(44.dp)
        ) {
            Text(
                "Главная",
                fontWeight = FontWeight.W400,
                fontSize = 32.sp,
                lineHeight = 32.75.sp,
                color = TextColor
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.highlight_05),
                contentDescription = "",
                modifier = Modifier.padding(bottom = 27.dp, end = 128.dp)
            )
        }
        CommonShopButton(
            state = false
        )
    }
}