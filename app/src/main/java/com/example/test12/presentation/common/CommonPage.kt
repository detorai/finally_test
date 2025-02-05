package com.example.test12.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.test12.presentation.ui.theme.Block
import com.example.test12.presentation.ui.theme.SubTextLight

@Composable
fun Page(@DrawableRes image: Int, label: String, description: String){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
            .padding(start = 30.dp, end = 30.dp, top = 80.dp)
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(image),
            contentDescription = "image",
            modifier = Modifier.size(375.dp, 302.dp),
            contentScale = ContentScale.Fit
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(top = 60.dp)
        ) {
            Text(
                label,
                textAlign = TextAlign.Center,
                color = Block
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 12.dp).fillMaxWidth().height(50.dp)
        ) {
            Text(
                description,
                color = SubTextLight,
                textAlign = TextAlign.Center
            )
        }
    }
}
