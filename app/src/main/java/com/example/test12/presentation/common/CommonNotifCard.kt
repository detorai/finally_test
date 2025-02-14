package com.example.test12.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test12.R
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.SubTextDark
import com.example.test12.presentation.ui.theme.TextColor

@Composable
fun CommonNotifCard(
    label: String,
    content: String,
    time: String
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth().wrapContentHeight().background(Background, RoundedCornerShape(14.dp)).padding(16.dp)
    ){
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        ) {
            Text(
                label,
                textAlign = TextAlign.Center,
                color = TextColor,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(Font(R.font.new_peninim_font)),

            )
            Text(
                content,
                textAlign = TextAlign.Center,
                color = TextColor,
                fontSize = 12.sp,
                lineHeight = 14.4.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(Font(R.font.new_peninim_font)),
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                time,
                color = SubTextDark,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                lineHeight = 14.4.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(Font(R.font.new_peninim_font)),
                modifier = Modifier.padding(top = 16.dp)

            )
        }
    }
}

@Preview
@Composable
fun check(){
    CommonNotifCard(
        "123",
        "123",
        "123"
    )
}