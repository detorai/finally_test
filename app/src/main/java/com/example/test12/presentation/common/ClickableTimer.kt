package com.example.test12.presentation.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.test12.R
import com.example.test12.presentation.ui.theme.SubTextDark
import kotlinx.coroutines.delay

@SuppressLint("DefaultLocale")
@Composable
fun ClickableTimer(
    onClick: () -> Unit,
    timerSeconds: Int = 5,
    modifier: Modifier
){
    var isClickable by remember { mutableStateOf(false) }
    var countdown by remember { mutableIntStateOf(timerSeconds) }
    var timerVisible by remember { mutableStateOf(true) }

    LaunchedEffect(timerVisible) {
        if (timerVisible) {
            countdown = timerSeconds
            isClickable = false
            while (countdown > 0) {
                delay(1000L)
                countdown--
            }
            isClickable = true
            timerVisible = false
        }
    }


    val minutes = countdown / 60
    val seconds = countdown % 60
    val timeDisplay = String.format("%02d:%02d", minutes, seconds)


    val annotatedText = buildAnnotatedString {
        pushStringAnnotation("click", "clickable")
        withStyle(SpanStyle(
            fontWeight = FontWeight.W400,
            fontSize = 12.sp,
            color = SubTextDark,
        )) {
            append("Отправить заново")
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                if (isClickable) {
                    annotatedText.getStringAnnotations(offset, offset)
                        .firstOrNull()
                        ?.let { annotation ->
                            if (annotation.item == "clickable" && isClickable) {
                                onClick()
                                isClickable = false
                                timerVisible = true
                            }
                        }
                }
            }
        )
        if(timerVisible) {
            Text(
                timeDisplay,
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                lineHeight = 12.28.sp,
                fontFamily = FontFamily(Font(R.font.new_peninim_font)),
                color = SubTextDark,
                textAlign = TextAlign.Right
            )
        }
    }
}