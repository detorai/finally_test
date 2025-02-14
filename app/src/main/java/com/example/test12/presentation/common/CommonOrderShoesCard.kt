package com.example.test12.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil.compose.AsyncImage
import com.example.test12.R
import com.example.test12.domain.shoes.Shoes
import com.example.test12.presentation.ui.theme.Accent
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.Block
import com.example.test12.presentation.ui.theme.Hint
import com.example.test12.presentation.ui.theme.Red
import com.example.test12.presentation.ui.theme.TextColor

@Composable
fun CommonOrderShoesCard(
    shoes: Shoes,
    orderNumber: String,
    orderDate: String,
    deliveryCost: String = "$260",
    onDelete: ()-> Unit,
    onReorder: () -> Unit,
    modifier: Modifier,
    onCardClick: (Shoes) -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(104.dp)
            .background(Background)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp)
                .background(color = Block, shape = RoundedCornerShape(8.dp))
                .clickable { onCardClick(shoes) }
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 10.dp)
                    .size(87.dp, 85.dp)
                    .background(color = Background, shape = RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = shoes.image,
                    contentDescription = "",
                    modifier = Modifier.size(86.dp, 55.dp)
                )
            }
            Text(
                orderDate,
                modifier = Modifier.align(Alignment.TopEnd),
                color = Hint
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 127.dp)
                    .height(39.dp)
            ) {
                Text(
                    "№ $orderNumber",
                    color = Accent
                )
                Text(
                    shoes.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 16.38.sp,
                    color = TextColor
                )
                Row {
                    Text(
                        "₽${shoes.cost}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        lineHeight = 14.33.sp,
                        color = TextColor
                    )
                    Text(
                        deliveryCost,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        lineHeight = 14.33.sp,
                        color = Hint,
                        modifier= Modifier.padding(start = 39.dp)
                    )
                }
            }
        }
    }
}