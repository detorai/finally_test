package com.example.test12.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.test12.presentation.ui.theme.Block

@Composable
fun CommonDialogError(
    onDismiss: ()-> Unit,
    errorText: String
){
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth().height(200.dp).background(Block, RoundedCornerShape(15.dp))
        ){
            Text(
                errorText
            )
        }
    }
}