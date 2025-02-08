package com.example.test12.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.test12.R
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.Hint
import com.example.test12.presentation.ui.theme.Typography

@Composable
fun CommonPersonalDataCheck(
    state: Boolean,
    onCheck: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier
){
    val annotatedText = buildAnnotatedString {
        pushStringAnnotation("click", "clickable")
        withStyle(
            SpanStyle(
                color = Hint,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Даю согласие на обработку\nперсональных данных")
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.fillMaxWidth()
    ){
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .size(18.dp)
                .background(color = Background, shape = RoundedCornerShape(6.dp))
                .clickable {
                    onCheck()
                }
        ){
            if (state){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.check_mark),
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }
        }
        ClickableText(
            text = annotatedText,
            maxLines = 2,
            modifier = Modifier.padding(start = 12.dp),
            style = Typography.displaySmall,
            onClick = { offset ->
                annotatedText.getStringAnnotations(offset, offset).firstOrNull()
                    ?.let { annotation ->
                        when (annotation.item) {
                            "clickable" -> onClick()
                        }
                    }
            }
        )
    }
}