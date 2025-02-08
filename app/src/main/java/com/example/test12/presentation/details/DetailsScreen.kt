package com.example.test12.presentation.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import com.example.test12.R
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.domain.category.Category
import com.example.test12.domain.shoes.Shoes
import com.example.test12.presentation.bucket.BucketScreen
import com.example.test12.presentation.common.CommonButtonWithIcon
import com.example.test12.presentation.common.CommonScaffold
import com.example.test12.presentation.ui.theme.Accent
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.Block
import com.example.test12.presentation.ui.theme.TextColor
import kotlinx.coroutines.launch

data class DetailsScreen(private val db: AppDatabase, private var selectedShoes: Shoes): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel { DetailsViewModel(db, selectedShoes) }
        CommonScaffold(
            content = { Details(
                navigator = navigator,
                paddingValues = PaddingValues(top = 118.dp, start = 20.dp, end = 20.dp),
                viewModel = viewModel,
                shoesState = selectedShoes
            ) },
            topBar = {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(PaddingValues(top = 48.dp, start = 20.dp, end = 20.dp))
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CommonButtonWithIcon(
                                onClick = {navigator.pop()},
                                modifier = Modifier.align(Alignment.CenterStart),
                                icon = R.drawable.arrow_back
                            )
                            Text(
                                "Sneaker Shop",
                                color = TextColor,
                            )
                                CommonButtonWithIcon(
                                    onClick = {navigator.push(BucketScreen(db))},
                                    modifier = Modifier.align(Alignment.CenterEnd),
                                    icon = R.drawable.bag_black
                                )
                        }
                    }

                }
            }
        )
    }
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Details(
        paddingValues: PaddingValues,
        navigator: Navigator,
        viewModel: DetailsViewModel,
        shoesState: Shoes
    ){
        val pagerState = rememberPagerState (pageCount = {viewModel.shoesList.size} )
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier.fillMaxSize().background(Background)
        ) {
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth().padding(paddingValues)) {
                Text(
                    shoesState.name,
                )
                Text(
                    "",
                    modifier = Modifier.padding(top = 13.dp)
                )
                Text(
                    shoesState.cost.toString(),
                    modifier = Modifier.padding(top = 14.dp)
                )
            }
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.padding(top = 25.dp).height(167.dp).fillMaxWidth()
            ){

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.75f)
                ) { page ->
                    AsyncImage(
                        model = viewModel.shoesList.find { it.id == shoesState.id }?.image,
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth
                    )
                }
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.stand),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
            LazyRow(
                modifier = Modifier.padding(top = 19.dp).fillMaxWidth().height(40.dp),
            ) {
                itemsIndexed(viewModel.shoesList) { index, shoes ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(108.dp, 40.dp)
                                    .background(
                                        color = Block,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clickable {
                                        selectedShoes = shoes
                                    }
                                ,
                                contentAlignment = Alignment.Center,
                            ) {
                                AsyncImage(
                                    model = shoes.image,
                                    contentDescription = "",
                                )
                            }
                            if (index < viewModel.shoesList.size - 1) {
                                Spacer(Modifier.width(14.dp))
                            }
                        }
                    }
            }
        }
    }
}
