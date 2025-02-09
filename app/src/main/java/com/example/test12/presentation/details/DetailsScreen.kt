package com.example.test12.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
import coil.compose.AsyncImage
import com.example.test12.R
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.domain.shoes.Shoes
import com.example.test12.presentation.bucket.BucketScreen
import com.example.test12.presentation.common.CommonButtonWithIcon
import com.example.test12.presentation.common.CommonDialogError
import com.example.test12.presentation.common.CommonScaffold
import com.example.test12.presentation.ui.theme.Accent
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.Block
import com.example.test12.presentation.ui.theme.Hint
import com.example.test12.presentation.ui.theme.SubTextLight
import com.example.test12.presentation.ui.theme.TextColor
import com.example.test12.presentation.ui.theme.Typography

data class DetailsScreen(private val db: AppDatabase, private var selectedShoes: Shoes): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = rememberScreenModel { DetailsViewModel(db, selectedShoes) }
        CommonScaffold(
            content = {
                Details(
                paddingValues = PaddingValues(top = 118.dp, start = 20.dp, end = 20.dp),
                viewModel = viewModel,
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
    @Composable
    fun Details(
        paddingValues: PaddingValues,
        viewModel: DetailsViewModel,
    ){
        val state = viewModel.state.collectAsState().value

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (state.isLoading) {
                Dialog(
                    onDismissRequest = {}
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Block, RoundedCornerShape(15.dp))
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            state.error?.let {
                CommonDialogError(
                    onDismiss = viewModel::resetError,
                    errorText = it
                )
            }
            Column(Modifier.fillMaxWidth()) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        state.selectedShoesState!!.name,
                    )
                    Text(
                        "${ viewModel.getCategoryById(state.selectedShoesState!!.category) }",
                        modifier = Modifier.padding(top = 14.dp)
                    )
                    Text(
                        state.selectedShoesState!!.cost.toString(),
                        modifier = Modifier.padding(top = 13.dp)
                    )
                }
                Box(
                    contentAlignment = Alignment.TopCenter,
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .height(167.dp)
                        .fillMaxWidth()
                ) {
                    AsyncImage(
                        model = state.selectedShoesState!!.image,
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.stand),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
                LazyRow(
                    modifier = Modifier
                        .padding(top = 19.dp)
                        .fillMaxWidth()
                        .height(40.dp),
                ) {
                    itemsIndexed(viewModel.shoesList) { index, shoes ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .background(
                                        color = Block,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clickable {
                                        viewModel.updateScreen(shoes)
                                    },
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
                Text(
                    state.selectedShoesState!!.description,
                    fontFamily = FontFamily(Font(R.font.new_peninim_font)),
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    color = Hint,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(top = 33.dp)
                        .fillMaxWidth()
                        .verticalScroll(ScrollState(0))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(52.dp)
                        .background(color = SubTextLight, shape = RoundedCornerShape(40.dp))
                        .clickable { viewModel.inFavourite(selectedShoes) }
                ){
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.heart),
                        contentDescription = "",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Button(
                    onClick = { viewModel.inBucket(selectedShoes) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonColors(
                        contentColor = Background,
                        disabledContentColor = Background,
                        containerColor = Accent,
                        disabledContainerColor = Accent
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(50.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ){
                        Text(
                            "В Корзину",
                            style = Typography.bodySmall,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.bag_splash),
                            contentDescription = "",
                            tint = Background,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 12.dp)
                                .size(24.dp)
                        )
                    }
                }
            }
        }
    }
}
