package com.example.test12.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.test12.R
import com.example.test12.presentation.ui.theme.SubTextDark
import com.example.test12.presentation.ui.theme.TextColor

@Composable
fun CommonClickableSign(
    clickable: String,
    nonClickable: String,
    onClick: () -> Unit,
    modifier: Modifier
){
    val annotatedText = buildAnnotatedString {
        withStyle(
            SpanStyle(
                color = SubTextDark,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(Font(R.font.new_peninim_font))
            )
        ){
            append(nonClickable)
        }
        pushStringAnnotation("click", "click")
        withStyle(
            SpanStyle(
                color = TextColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(Font(R.font.new_peninim_font))
            )
        ){
            append(clickable)
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        ClickableText(
            text = annotatedText,
            onClick = {offset ->
                annotatedText.getStringAnnotations(offset,offset).firstOrNull()?.let { annotation ->
                    when (annotation.item) {
                        "click" -> { onClick() }
                    }
                }
            }
        )
    }
}