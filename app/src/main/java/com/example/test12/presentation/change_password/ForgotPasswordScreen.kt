package com.example.test12.presentation.change_password

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test12.R
import com.example.test12.presentation.common.CommonButton
import com.example.test12.presentation.common.CommonButtonWithIcon
import com.example.test12.presentation.common.CommonDialogError
import com.example.test12.presentation.common.CommonSignLabel
import com.example.test12.presentation.common.CommonTextField
import com.example.test12.presentation.ui.theme.Accent
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.Block
import com.example.test12.presentation.ui.theme.SubTextDark
import com.example.test12.presentation.ui.theme.TextColor
import com.example.test12.presentation.ui.theme.Typography
import com.example.test12.presentation.verification.OTPScreen

class ForgotPasswordScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel { ForgotPasswordViewModel() }
        ForgotPassword(navigator, viewModel)
    }
    @Composable
    fun ForgotPassword(
        navigator: Navigator,
        viewModel: ForgotPasswordViewModel
    ){
        val state = viewModel.state.collectAsState().value
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize().background(Block).padding(horizontal = 20.dp)
        ) {
            if (state.success) {
                Dialog(
                    onDismissRequest = {navigator.push(OTPScreen(state.email))}
                ) {
                    Box(
                        contentAlignment = Alignment.TopCenter,
                        modifier = Modifier.fillMaxWidth().height(196.dp).background(Block, RoundedCornerShape(16.dp))
                    ){
                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(color = Accent, shape = RoundedCornerShape(40.dp))
                                    .padding(top = 30.dp)
                            ){
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.email_dialog),
                                    contentDescription = "",
                                    tint = Color.Unspecified
                                )
                            }
                            Text(
                                "Проверьте Ваш Email",
                                style = Typography.labelSmall,
                                color = TextColor,
                                modifier = Modifier.padding(top = 24.dp)
                            )
                            Text(
                                "Мы отправили код восстановления\nпароля на вашу электронную почту.",
                                style = Typography.labelSmall,
                                color = SubTextDark,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(top = 66.dp)) {
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
                    label = "Забыл пароль",
                    desc = "Введите свою учетную запись\n" +
                            "для сброса",
                    modifier = Modifier.padding(top = 11.dp)
                )
                CommonTextField(
                    value = state.email,
                    onValueChange = viewModel::onEmail,
                    modifier = Modifier.padding(top = 40.dp),
                    state = false
                )
                CommonButton(
                    modifier = Modifier.padding(top = 40.dp),
                    text = "Отправить",
                    textColor = Background,
                    buttonColor = Accent,
                    onClick = {
                        viewModel.resetPassword(state.email)
                    }
                )
            }
        }
    }
}