package com.example.test12.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.test12.presentation.ui.theme.Typography

@Composable
fun CommonButton(
    modifier: Modifier,
    onClick: ()-> Unit,
    textColor: Color,
    buttonColor: Color,
    text: String
){
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonColors(
            contentColor = textColor,
            disabledContentColor = textColor,
            containerColor = buttonColor,
            disabledContainerColor = buttonColor
        ),
        modifier = modifier.fillMaxWidth().height(50.dp)
    ) {
        Text(
            text,
            style = Typography.bodySmall
        )
    }
}