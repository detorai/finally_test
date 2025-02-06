package com.example.test12.presentation.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test12.R
import com.example.test12.presentation.ui.theme.Accent
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.Block
import com.example.test12.presentation.ui.theme.SubTextDark
import com.example.test12.presentation.ui.theme.TextColor
import com.example.test12.presentation.ui.theme.Typography

@Composable
fun CommonBucketBar(
    onClick: ()-> Unit,
    cost: Double,
    delivery: Double,
    sum: Double
){
    Column(
        modifier = Modifier.fillMaxWidth().height(258.dp).background(Block).padding(20.dp, 32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(
                    "Сумма",
                    style = Typography.labelSmall,
                    color = SubTextDark
                )
                Text(
                    cost.toString(),
                    color = TextColor,
                    textAlign = TextAlign.Right,
                    fontSize = 16.sp,
                    lineHeight = 16.38.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily(Font(R.font.new_peninim_font))
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Доставка",
                    style = Typography.labelSmall,
                    color = SubTextDark
                )
                Text(
                    delivery.toString(),
                    color = TextColor,
                    textAlign = TextAlign.Right,
                    fontSize = 16.sp,
                    lineHeight = 16.38.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily(Font(R.font.new_peninim_font))
                )
            }
            Canvas(
                modifier = Modifier.padding(top = 18.dp).fillMaxWidth().height(1.dp)
            ) {
                val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                drawLine(
                    color = SubTextDark,
                    start = Offset(0f, size.height /2),
                    end = Offset(size.width, size.height /2),
                    strokeWidth = 2f,
                    pathEffect = pathEffect
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Итого",
                    style = Typography.labelSmall,
                )
                Text(
                    sum.toString(),
                    color = TextColor,
                    textAlign = TextAlign.Right,
                    fontSize = 16.sp,
                    lineHeight = 16.38.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily(Font(R.font.new_peninim_font))
                )
            }
        }
        CommonButton(
            modifier = Modifier,
            onClick = onClick,
            buttonColor = Accent,
            textColor = Background,
            text = "Оформить заказ"
        )
    }
}