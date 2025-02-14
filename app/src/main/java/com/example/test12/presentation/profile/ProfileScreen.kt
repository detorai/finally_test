package com.example.test12.presentation.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.test12.R
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.presentation.bucket.BucketScreen
import com.example.test12.presentation.common.CommonBottomBar
import com.example.test12.presentation.common.CommonButton
import com.example.test12.presentation.common.CommonScaffold
import com.example.test12.presentation.common.CommonSideMenu
import com.example.test12.presentation.common.CommonTextField
import com.example.test12.presentation.home.HomeScreen
import com.example.test12.presentation.notification.NotificationScreen
import com.example.test12.presentation.secondary_screen.ScreenType
import com.example.test12.presentation.secondary_screen.SecondaryScreen
import com.example.test12.presentation.ui.theme.Accent
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.Block
import com.example.test12.presentation.ui.theme.TextColor
import com.example.test12.presentation.ui.theme.Typography
import kotlinx.coroutines.launch

data class ProfileScreen(private val db: AppDatabase): Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { ProfileViewModel() }
        val navigator = LocalNavigator.currentOrThrow
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        CommonSideMenu(
            profilePhoto = "",
            userName = "123",
            navigator = navigator,
            db = db,
            drawState = drawerState,
            content = {
                CommonScaffold(
                    topBar = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(Block)
                                .padding(top = 48.dp, start = 20.dp, end = 20.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) {
                                            drawerState.open()
                                        } else {
                                            drawerState.close()
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.hamburger),
                                    contentDescription = "",
                                    tint = Color.Unspecified
                                )
                            }
                            Text(
                                "Профиль",
                                style = Typography.labelSmall,
                                color = TextColor
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(25.dp)
                                    .background(Accent, CircleShape)
                                    .clickable {
                                        viewModel.editProfile()
                                    }
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.pencil),
                                    contentDescription = "",
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    },
                    content = {
                        Profile(
                            PaddingValues(top = 130.dp),
                            viewModel
                        )
                    },
                    bottomBar = {
                        CommonBottomBar(
                            onClickBucket = {
                                navigator.push(BucketScreen(db))
                            },
                            onClickFavour = {
                                navigator.push(SecondaryScreen(ScreenType.FAVOURITE, db = db))
                            },
                            onClickHome = {navigator.push(HomeScreen(db))},
                            onClickProfile = {},
                            onClickNotif = {navigator.push(NotificationScreen(db))}
                        )
                    }
                )
            }
        )
    }

    @Composable
    fun Profile(
        paddingValues: PaddingValues,
        viewModel: ProfileViewModel
    ){
        val  uri = remember { mutableStateOf<Uri?>(null) }
        val pickMedia = rememberLauncherForActivityResult (ActivityResultContracts.PickVisualMedia()){
            if(it != null){
                uri.value = it
            }
        }
        val state = viewModel.state.collectAsState().value
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Block)
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            if (state.choosePicture){
                Dialog(
                    onDismissRequest = { viewModel.choosePicture() }
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().background(Block).padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CommonButton(
                            modifier = Modifier,
                            text = "Галерея",
                            textColor = Block,
                            buttonColor = Accent,
                            onClick = {
                                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                viewModel.choosePicture()
                            }
                        )
                        CommonButton(
                            modifier = Modifier,
                            text = "Камера",
                            textColor = Block,
                            buttonColor = Accent,
                            onClick = {}
                        )
                        CommonButton(
                            modifier = Modifier,
                            text = "Сгенерировать",
                            textColor = Block,
                            buttonColor = Accent,
                            onClick = {}
                        )
                    }
                }
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(96.dp)
            ){
                if (uri.value != null){
                    AsyncImage(
                        model = uri.value,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Text(
                "${state.name} ${state.se_name}",
                textAlign = TextAlign.Center,
                color = TextColor,
                fontSize = 20.sp,
                lineHeight = 20.47.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily(Font(R.font.new_peninim_font))
            )
            if (state.editState){
                val annotatedText = buildAnnotatedString {
                    pushStringAnnotation("click","click")
                    withStyle(
                        SpanStyle(
                            color = Accent
                        )
                    ){
                        append("Изменить фото профиля")
                    }
                }
                ClickableText(
                    text = annotatedText,
                    onClick = {offset ->
                        annotatedText.getStringAnnotations(offset,offset).firstOrNull()?.let { annotation ->
                            when(annotation.item) {
                                "click" -> {
                                    viewModel.choosePicture()
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 11.dp)
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(67.dp)
                        .background(
                            Background,
                            RoundedCornerShape(16.dp)
                        )
                        .padding(8.dp)
                ) {
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.qr),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Имя",
                    style = Typography.labelSmall,
                    modifier = Modifier.padding(top = 19.dp)
                )
                CommonTextField(
                    value = state.name,
                    onValueChange = {},
                    modifier = Modifier.padding(top = 19.dp),
                    state = false
                )
                Text(
                    "Фамилия",
                    style = Typography.labelSmall,
                    modifier = Modifier.padding(top = 19.dp),
                )
                CommonTextField(
                    value = state.se_name,
                    onValueChange = {},
                    modifier = Modifier.padding(top = 19.dp),
                    state = false
                )
                Text(
                    "Адрес",
                    style = Typography.labelSmall,
                    modifier = Modifier.padding(top = 19.dp),
                )
                CommonTextField(
                    value = state.address,
                    onValueChange ={},
                    modifier = Modifier.padding(top = 19.dp),
                    state = false
                )
                Text(
                    "Телефон",
                    style = Typography.labelSmall,
                    modifier = Modifier.padding(top = 19.dp),
                )
                CommonTextField(
                    value = state.number,
                    onValueChange = {},
                    modifier = Modifier.padding(top = 19.dp),
                    state = false
                )
            }
        }
    }
}