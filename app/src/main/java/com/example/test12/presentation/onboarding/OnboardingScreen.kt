package com.example.test12.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test12.R
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.presentation.common.Page
import com.example.test12.presentation.home.HomeScreen
import com.example.test12.presentation.ui.theme.Block
import com.example.test12.presentation.ui.theme.Disabled
import com.example.test12.presentation.ui.theme.TextColor
import kotlinx.coroutines.launch

data class OnboardingScreen(
    private val db: AppDatabase
): Screen {
    @Composable
    override fun Content() {
        Onboarding(db)
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Onboarding(db: AppDatabase){
        val pagerState = rememberPagerState (pageCount = {3} )
        var visible by remember { mutableStateOf(true) }
        val coroutineScope = rememberCoroutineScope()

        val pagerImage = listOf(0, R.drawable.image_2, R.drawable.image_3)
        val pagerLabel = listOf("", "Начнем\nпутешествие", "Умная, великолепная и модная\nколлекция Изучите сейчас")
        val pagerDesc = listOf("", "Умная, великолепная и модная\nколлекция Изучите сейчас", "В вашей комнате много красивых и\nпривлекательных растений")

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.isScrollInProgress }.collect{ page ->
                visible = !page
            }
        }

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF48B2E7),
                        Color(0xFF44A9DC),
                        Color(0xFF2B6B8B)
                    )
                )
            )
        ){
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.75f)
            ) { page ->
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(
                        initialAlpha = 0.1f
                    ),
                    exit = fadeOut(
                        animationSpec = tween(durationMillis = 70)
                    )
                ) {
                    if (page == 0){
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(top = 70.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "ДОБРО\nПОЖАЛОВАТЬ",
                                    textAlign = TextAlign.Center,
                                    color = Block
                                )
                            }
                            Image(
                                bitmap = ImageBitmap.imageResource(R.drawable.image_1),
                                contentDescription = "image",
                                modifier = Modifier.size(375.dp, 302.dp).padding(bottom = 26.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    else {
                        Page(
                            image = pagerImage[page],
                            label = pagerLabel[page],
                            description = pagerDesc[page],
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(start = 20.dp, end = 20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val current = pagerState.currentPage == iteration
                        Box(
                            modifier = Modifier
                                .width(if (current) 48.dp else 23.dp)
                                .height(4.dp)
                                .background(
                                    color = (if (current) Block else Disabled),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .padding(horizontal = 4.dp),

                            )
                        if (pagerState.pageCount > 0) {
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                    }
                }

                Button(
                    modifier = Modifier.padding(bottom = 36.dp).fillMaxWidth().height(50.dp),
                    onClick = {
                        coroutineScope.launch {
                            println(pagerState.currentPage)
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                        if (pagerState.pageCount == pagerState.currentPage + 1) {
                            navigator.push(HomeScreen(db))
                        }
                    },
                    shape = RoundedCornerShape(13.dp),
                    colors = ButtonColors(
                        containerColor = Block,
                        disabledContainerColor = Block,
                        contentColor = TextColor,
                        disabledContentColor = TextColor
                    )
                ) {
                    Text(
                        text = if (pagerState.currentPage == 0) "Начать" else "Далее",
                        )
                }
            }
        }
    }
}