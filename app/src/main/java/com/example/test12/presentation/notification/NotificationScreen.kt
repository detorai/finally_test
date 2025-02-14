package com.example.test12.presentation.notification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test12.R
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.presentation.bucket.BucketScreen
import com.example.test12.presentation.common.CommonBottomBar
import com.example.test12.presentation.common.CommonNotifCard
import com.example.test12.presentation.common.CommonScaffold
import com.example.test12.presentation.common.CommonSideMenu
import com.example.test12.presentation.home.HomeScreen
import com.example.test12.presentation.profile.ProfileScreen
import com.example.test12.presentation.secondary_screen.ScreenType
import com.example.test12.presentation.secondary_screen.SecondaryScreen
import com.example.test12.presentation.ui.theme.Block
import com.example.test12.presentation.ui.theme.TextColor
import com.example.test12.presentation.ui.theme.Typography
import kotlinx.coroutines.launch

data class NotificationScreen(private val db: AppDatabase): Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { NotificationViewModel() }
        val navigator = LocalNavigator.currentOrThrow
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
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
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .background(Block)
                                    .padding(top = 48.dp, start = 20.dp, end = 20.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    IconButton(
                                        modifier = Modifier.align(Alignment.CenterStart),
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
                                        "Уведомления",
                                        style = Typography.labelSmall,
                                        color = TextColor
                                    )
                                }
                            }
                        },
                        content = {
                            Notification(PaddingValues(top = 108.dp), viewModel)
                        },
                        bottomBar = {
                            CommonBottomBar(
                                onClickBucket = {
                                    navigator.push(BucketScreen(db))
                                },
                                onClickFavour = {
                                    navigator.push(SecondaryScreen(ScreenType.FAVOURITE, db = db))
                                },
                                onClickHome = { navigator.push(HomeScreen(db)) },
                                onClickProfile = {navigator.push(ProfileScreen(db))},
                                onClickNotif = {}
                            )
                        }
                    )
                }
            )
            if (!drawerState.isClosed){
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.side_menu),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(160.dp)
                        .align(Alignment.CenterEnd),
                    contentScale = ContentScale.Fit

                )
            }
        }
    }

    @Composable
    fun Notification(
        paddingValues: PaddingValues,
        viewModel: NotificationViewModel
    ){
        val state = viewModel.state.collectAsState().value
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Block)
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
        ) {
            itemsIndexed(state.notifList){index, item ->
                CommonNotifCard(
                    label = item.label,
                    content = item.content,
                    time = "${ item.time }".replace("T",",").replace("-",".")
                )
                if (index < state.notifList.size-1){
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}