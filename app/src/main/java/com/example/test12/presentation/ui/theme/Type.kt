package com.example.test12.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.test12.R

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.new_peninim_font)),
        fontWeight = FontWeight.W400,
        fontSize = 32.sp,
        lineHeight = 32.75.sp,
        textAlign = TextAlign.Center,
        color = TextColor
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.new_peninim_font)),
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        textAlign = TextAlign.Center,
        color = SubTextDark
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.new_peninim_font)),
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 22.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.new_peninim_font)),
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        color = TextColor
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.new_peninim_font)),
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 16.38.sp,
    ),
)