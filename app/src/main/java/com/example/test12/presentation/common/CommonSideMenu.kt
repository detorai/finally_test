package com.example.test12.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import coil.compose.AsyncImage
import com.example.test12.R
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.presentation.sign_in.SignInScreen
import com.example.test12.presentation.ui.theme.Accent
import com.example.test12.presentation.ui.theme.Block

@Composable
fun CommonSideMenu(
    profilePhoto: String,
    userName: String,
    navigator: Navigator,
    db: AppDatabase,
    content: @Composable () -> Unit = {},
    drawState: DrawerState,
    signOut:() -> Unit = {}
){
    ModalNavigationDrawer(
        drawerState = drawState,
        scrimColor = Accent,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Accent) {
                Column(
                    modifier = Modifier.fillMaxSize().background(Accent)
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(top = 78.dp).size(96.dp).background(shape = RoundedCornerShape(40.dp), color = Color.Transparent)
                    ) {
                        AsyncImage(
                            model = profilePhoto,
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Row(
                        modifier = Modifier.padding(top = 15.dp).fillMaxWidth()
                    ) {
                        Text(
                            text = userName,
                            color = Block,
                            lineHeight = 20.47.sp,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W400,
                            fontFamily = FontFamily(Font(R.font.new_peninim_font)),
                        )
                    }
                    NavigationRow(
                        text = "Профиль",
                        icon = R.drawable.profile,
                        onNavigate = {  },
                        modifier = Modifier.padding(top = 58.dp)
                    )
                    NavigationRow(
                        text = "Корзина",
                        icon = R.drawable.bag_black,
                        onNavigate = {  },
                        modifier = Modifier.padding(top = 30.dp)
                    )
                    NavigationRow(
                        text = "Избранное",
                        icon = R.drawable.heart,
                        onNavigate = {  },
                        modifier = Modifier.padding(top = 30.dp)
                    )
                    NavigationRow(
                        text = "Заказы",
                        icon = R.drawable.orders,
                        onNavigate = {  },
                        modifier = Modifier.padding(top = 30.dp)
                    )
                    NavigationRow(
                        text = "Уведомления",
                        icon = R.drawable.notif,
                        onNavigate = {  },
                        modifier = Modifier.padding(top = 30.dp)
                    )
                    NavigationRow(
                        text = "Настройки",
                        icon = R.drawable.settings,
                        onNavigate = {  },
                        modifier = Modifier.padding(top = 30.dp)
                    )
                    HorizontalDivider(modifier = Modifier.padding(top = 38.dp))
                    NavigationRow(
                        text = "Выйти",
                        icon = R.drawable.sign_out,
                        onNavigate = {

                            navigator.push(SignInScreen(db))

                        },
                        modifier = Modifier.padding(top = 30.dp)
                    )
                }
            }
        }
    ) {
        content()
    }
}
@Composable
fun NavigationRow(
    onNavigate: () -> Unit,
    @DrawableRes icon: Int ,
    text: String,
    modifier: Modifier
){
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.fillMaxWidth().background(Accent).clickable { onNavigate() }
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = "",
                tint = Block,
                modifier = Modifier.align(Alignment.CenterStart).size(24.dp)
            )
            Text(
                text,
                color = Block,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 56.dp)
            )
        }
    }
}
