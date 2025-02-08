package com.example.test12.presentation.verification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test12.R
import com.example.test12.presentation.common.ClickableTimer
import com.example.test12.presentation.common.CommonButton
import com.example.test12.presentation.common.CommonButtonWithIcon
import com.example.test12.presentation.common.CommonSignLabel
import com.example.test12.presentation.common.CommonTextField
import com.example.test12.presentation.home.HomeScreen
import com.example.test12.presentation.ui.theme.Accent
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.Block
import com.example.test12.presentation.ui.theme.TextColor

data class OTPScreen(private val email: String): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel { OTPViewModel(email) }
        OTP(navigator, viewModel)
    }
    @Composable
    fun OTP(
        navigator: Navigator,
        viewModel: OTPViewModel
    )
    {
        val state = viewModel.state.collectAsState().value
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .background(Block)
                .padding(horizontal = 20.dp)
        ) {
            if (state.success){
                Dialog(
                    onDismissRequest = {
                        if (state.passwordState)
                            viewModel.updateUser(state.newPassword)
                            state.passwordState = false

                    }
                ) {
                    Box(
                        contentAlignment = Alignment.TopCenter,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(196.dp)
                            .background(Block, RoundedCornerShape(16.dp))
                    ){
                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            if (!state.passwordState){
                                Text(
                                    "Введите новый пароль"
                                )
                                CommonTextField(
                                    value = state.newPassword,
                                    onValueChange = viewModel::onNewPass,
                                    modifier = Modifier.padding(top = 40.dp),
                                    state = false
                                )
                                CommonButton(
                                    onClick = {
                                        viewModel.generatePassword(state.newPassword)

                                    },
                                    modifier = Modifier,
                                    text = "Confirm",
                                    textColor = Background,
                                    buttonColor = Accent
                                )
                            } else  {
                                Text(
                                    state.newPassword
                                )
                            }
                        }
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 66.dp)) {
                    CommonButtonWithIcon(
                        modifier = Modifier.background(
                            color = Block,
                            shape = RoundedCornerShape(40.dp)
                        ),
                        onClick = { navigator.pop() },
                        icon = R.drawable.arrow_back
                    )
                }
                CommonSignLabel(
                    label = "OTP проверка",
                    desc = "Пожалуйста, проверьте сво\nэлектронную почту, чтобы увидеть код\nподтверждения",
                    modifier = Modifier.padding(top = 11.dp)
                )
                Text(
                    "OTP Код",
                    textAlign = TextAlign.Center,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 21.49.sp,
                    color = TextColor,
                    modifier = Modifier.padding(top = 35.dp)
                )
                CommonTextField(
                    value = state.otpCode,
                    onValueChange = viewModel::onOTP,
                    modifier = Modifier
                        .height(99.dp)
                        .padding(top = 12.dp),
                    state = false
                )
                ClickableTimer(
                    onClick = {},
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }
    }
}