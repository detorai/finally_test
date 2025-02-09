package com.example.test12.presentation.bucket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.presentation.common.CommonBucketBar
import com.example.test12.presentation.common.CommonDialogError
import com.example.test12.presentation.common.CommonScaffold
import com.example.test12.presentation.common.CommonShoesBucketCard
import com.example.test12.presentation.common.CommonTopBar
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.Block
import com.example.test12.presentation.ui.theme.TextColor

data class BucketScreen(private val db: AppDatabase): Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { BucketViewModel(db) }
        val navigator = LocalNavigator.currentOrThrow
        val delivery = viewModel.delivery.doubleValue
        CommonScaffold(
            content = {
                Bucket(
                    viewModel,
                    PaddingValues(top = 108.dp)
                )
            },
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(PaddingValues(top = 48.dp, start = 20.dp, end = 20.dp))
                ){
                    CommonTopBar(
                        label = "Корзина",
                        onBack = { navigator.pop() },
                    )
                }
            },
            bottomBar = {
                CommonBucketBar(
                    onClick = {},
                    cost = viewModel.shoesList.sumOf { it.cost * it.count },
                    delivery = delivery,
                    sum = delivery + viewModel.shoesList.sumOf { it.cost * it.count }
                )
            }
        )
    }

    @Composable
    fun Bucket(
        viewModel: BucketViewModel,
        paddingValues: PaddingValues
    ) {
        val state = viewModel.state.collectAsState().value
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Background)
                .padding(horizontal = 20.dp)
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
            Column(
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    "${viewModel.shoesList.size} товара",
                    fontSize = 16.sp,
                    lineHeight = 16.38.sp,
                    fontWeight = FontWeight.W400,
                    color = TextColor,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(viewModel.shoesList) { index, shoes ->
                        Column(
                            Modifier.fillMaxWidth()
                        ) {
                            CommonShoesBucketCard(
                                shoes = shoes,
                                onDown = { viewModel.countMinus(shoes)},
                                onDelete = { viewModel.deleteFromBucket(shoes)},
                                onUp = { viewModel.countPlus(shoes)},
                                modifier = Modifier,
                                onCardClick = {}
                            )
                            if (index < viewModel.shoesList.size - 1) {
                                Spacer(Modifier.height(14.dp))
                            }
                        }
                    }
                }
            }

        }
    }
}