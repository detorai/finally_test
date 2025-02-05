package com.example.test12.presentation.bucket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.presentation.common.CommonScaffold
import com.example.test12.presentation.common.CommonShoesBucketCard
import com.example.test12.presentation.common.CommonTopBar
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.TextColor

data class BucketScreen(private val db: AppDatabase): Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { BucketViewModel(db) }
        val navigator = LocalNavigator.currentOrThrow
        CommonScaffold(
            content = {
                Bucket(
                    viewModel,
                    it
                )
            },
            topBar = {
                CommonTopBar(
                    label = "Корзина",
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .background(Background),
                    onBack = { navigator.pop()},
                )
            }
        )
    }

    @Composable
    fun Bucket(
        viewModel: BucketViewModel,
        paddingValues: PaddingValues
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding()
                .fillMaxSize()
                .background(Background)
                .padding(horizontal = 20.dp)
        ) {
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
                                onDown = { },
                                onDelete = { },
                                onUp = { },
                                modifier = Modifier
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