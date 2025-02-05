package com.example.test12.presentation.sign_in

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen

class SignInScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { SignInViewModel() }
        SignIn(viewModel)
    }

    @Composable
    fun SignIn(viewModel: SignInViewModel){
        val state = viewModel.state.collectAsState().value
        TextField(
            value = state.email,
            onValueChange = viewModel::onEmail,
        )
        TextField(
            value = state.password,
            onValueChange = viewModel::onPassword,
        )
        Button(
            onClick = {
                viewModel.auth(state.email, state.password)
            }
        ) {

        }
        state.Error?.let {
            Dialog(onDismissRequest = { viewModel.resetError() }) {
                Box(
                    Modifier.height(200.dp).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        state.Error!!
                    )
                }
            }
        }
    }
}