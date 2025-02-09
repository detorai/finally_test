package com.example.test12.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.presentation.bucket.BucketScreen
import com.example.test12.presentation.common.CommonBottomBar
import com.example.test12.presentation.common.CommonCategoryRow
import com.example.test12.presentation.common.CommonDialogError
import com.example.test12.presentation.common.CommonHomeTopBar
import com.example.test12.presentation.common.CommonPopularRow
import com.example.test12.presentation.common.CommonSalesRow
import com.example.test12.presentation.common.CommonScaffold
import com.example.test12.presentation.common.CommonSearchRow
import com.example.test12.presentation.details.DetailsScreen
import com.example.test12.presentation.secondary_screen.ScreenType
import com.example.test12.presentation.secondary_screen.SecondaryScreen
import com.example.test12.presentation.ui.theme.Background
import com.example.test12.presentation.ui.theme.Block

data class HomeScreen(private val db: AppDatabase): Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { HomeViewModel(db) }
        val navigator = LocalNavigator.currentOrThrow
        LaunchedEffect(Unit) {
            viewModel.updateData()
        }
        CommonScaffold(
            topBar = {
                CommonHomeTopBar()
            },
            content = {
                Home(
                    navigator = navigator,
                    viewModel = viewModel,
                    db = db,
                    modifier = Modifier.padding(PaddingValues(top = 111.dp))
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
                    onClickHome = {}
                )
            }
        )
    }

    @Composable
    fun Home(
        navigator: Navigator,
        viewModel: HomeViewModel,
        db: AppDatabase,
        modifier: Modifier
    ) {
        val state = viewModel.state.collectAsState().value
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = modifier
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
            CommonSearchRow(Modifier.padding(top = 21.dp))
            CommonCategoryRow(
                modifier = Modifier.padding(top = 22.dp),
                categories = state.category,
                onClick = {
                    navigator.push(SecondaryScreen(ScreenType.CATEGORY, categoryScreen = it, db = db))
                },
                color = { Block }
            )
            CommonPopularRow(
                modifier = Modifier.padding(top = 24.dp),
                shoesList = viewModel.shoesList,
                onTextClick = {
                    navigator.push(SecondaryScreen(ScreenType.POPULAR, db = db))
                },
                onAdd = {
                    viewModel.inBucket(shoes = it)
                },
                onFavourite = {
                    viewModel.inFavourite(shoes = it)
                },
                onCardClick = {navigator.push(DetailsScreen(db, it))}
            )
            CommonSalesRow(
                modifier = Modifier.padding(top = 29.dp),
                onTextClick = {},
                listSales = state.sales
            )
        }
    }
}
