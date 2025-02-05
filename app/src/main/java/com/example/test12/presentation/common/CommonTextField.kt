package com.example.test12.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test12.R
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.Typography

@Composable
fun CommonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    visState: Boolean = false,
    onVis: ()-> Unit = {},
    state: Boolean
){
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        maxLines = 1,
        decorationBox = {inputText ->
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = modifier.fillMaxWidth().height(48.dp).background(Background, RoundedCornerShape(15.dp))
            ){
                inputText()
                if (state) {
                    Icon(
                        imageVector = ImageVector.vectorResource(if (!visState) R.drawable.unvis else R.drawable.vis),
                        contentDescription = "",
                        modifier = Modifier.align(Alignment.CenterEnd).padding(end = 12.dp)
                            .size(24.dp).clickable {
                            onVis()
                        }
                    )
                }
            }
        },
    )
}