package com.example.test12.presentation.sign_in

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test12.R
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.presentation.common.CommonButton
import com.example.test12.presentation.common.CommonClickableSign
import com.example.test12.presentation.common.CommonDialogError
import com.example.test12.presentation.common.CommonSignLabel
import com.example.test12.presentation.common.CommonTextField
import com.example.test12.presentation.home.HomeScreen
import com.example.test12.presentation.ui.theme.Accent
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.Block
import com.example.test12.presentation.ui.theme.SubTextDark
import com.example.test12.presentation.ui.theme.Typography

data class SignInScreen(private val db: AppDatabase): Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { SignInViewModel() }
        SignIn(viewModel)
    }

    @Composable
    fun SignIn(viewModel: SignInViewModel){
        val state = viewModel.state.collectAsState().value
        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(state.isSignIn) {
            if (state.isSignIn) navigator.push(HomeScreen(db))
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize().background(Block).padding(horizontal = 20.dp)
        ) {
            state.Error?.let {
                CommonDialogError(
                    onDismiss = viewModel::resetError,
                    errorText = state.Error!!
                )
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                CommonSignLabel(
                    label = "Привет!",
                    desc = "Заполните Свои данные или\nпродолжите через социальные медиа",
                    modifier = Modifier.padding(top = 121.dp)
                )
                Text(
                    "Email",
                    style = Typography.labelSmall,
                    modifier = Modifier.padding(top = 35.dp)
                )
                CommonTextField(
                    value = state.email,
                    onValueChange = viewModel::onEmail,
                    modifier = Modifier.padding(top = 12.dp),
                    state = false
                )
                Text(
                    "Пароль",
                    style = Typography.labelSmall,
                    modifier = Modifier.padding(top = 26.dp)
                )
                CommonTextField(
                    value = state.password,
                    onValueChange = viewModel::onPassword,
                    modifier = Modifier.padding(top = 12.dp),
                    onVis = viewModel::onPasswordVis,
                    visState = state.passwordVisible,
                    state = true,
                    visualTransformation = if (!state.passwordVisible) PasswordVisualTransformation() else VisualTransformation.None
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                ) {
                    val annotatedText = buildAnnotatedString {
                        pushStringAnnotation("click", "click")
                        withStyle(
                            SpanStyle(
                                color = SubTextDark,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W400,
                                fontFamily = FontFamily(Font(R.font.new_peninim_font))
                            )
                        ) {
                            append("Восстановить")
                        }
                    }
                    ClickableText(
                        text = annotatedText,
                        onClick = { offset ->
                            annotatedText.getStringAnnotations(offset, offset).firstOrNull()
                                ?.let { annotation ->
                                    when (annotation.item) {
                                        "click" -> {}
                                    }
                                }
                        }
                    )
                }
                CommonButton(
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = {viewModel.auth(state.email, state.password)},
                    text = "Войти",
                    textColor = Background,
                    buttonColor = Accent
                )
            }
            CommonClickableSign(
                clickable = "Создать пользователя",
                nonClickable = "Вы впервые?",
                onClick = {},
                modifier = Modifier.padding(bottom = 50.dp)
            )
        }
    }
}