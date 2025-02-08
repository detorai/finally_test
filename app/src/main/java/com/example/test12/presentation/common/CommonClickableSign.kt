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
import androidx.compose.ui.text.withStyle
import com.example.test12.presentation.ui.theme.SubTextDark
import com.example.test12.presentation.ui.theme.TextColor
import com.example.test12.presentation.ui.theme.Typography

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
            )
        ){
            append(nonClickable)
        }
        pushStringAnnotation("click", "click")
        withStyle(
            SpanStyle(
                color = TextColor,
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
            style = Typography.displaySmall,
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