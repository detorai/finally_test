package com.example.test12.presentation.secondary_sreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.domain.category.Category
import com.example.test12.presentation.bucket.BucketScreen
import com.example.test12.presentation.common.CommonBottomBar
import com.example.test12.presentation.common.CommonCategoryRow
import com.example.test12.presentation.common.CommonScaffold
import com.example.test12.presentation.common.CommonShoesCard
import com.example.test12.presentation.common.CommonTopBar
import com.example.test12.presentation.details.DetailsScreen
import com.example.test12.presentation.home.HomeScreen
import com.example.test12.presentation.ui.theme.Accent
import com.example.test12.presentation.ui.theme.Block

data class SecondaryScreen(
    private val screen: ScreenType,
    private val categoryScreen: Category? = null,
    private val db: AppDatabase
): Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { SecondaryViewModel(screen, categoryScreen, db) }
        LaunchedEffect(screen) {
            viewModel.updateScreen(screen)
        }
        key(screen) {
            Secondary(viewModel)
        }
    }
    @Composable
    fun Secondary(viewModel: SecondaryViewModel){
        val state = viewModel.state.collectAsState().value
        val navigator = LocalNavigator.currentOrThrow

        CommonScaffold(
            topBar = {
                Column(
                    Modifier.fillMaxWidth().padding(horizontal = 20.dp).padding(top = 48.dp)
                ) {
                    CommonTopBar(
                        onBack = { navigator.pop() },
                        label = state.label,
                        onFavourite = {
                            viewModel.updateScreen(screen)
                            navigator.push(
                                SecondaryScreen(screen = ScreenType.FAVOURITE, db = db)
                            )
                        },
                        screenType = screen
                    )
                    if (screen == ScreenType.CATEGORY) {
                        CommonCategoryRow(
                            modifier = Modifier.padding(top = 16.dp),
                            categories = state.category,
                            onClick = { viewModel.selectedCategory(it) },
                            color = { if (state.selectedCategory == it) Accent else Block }
                        )
                    }
                }
            },
            bottomBar = {
                if (screen == ScreenType.FAVOURITE) {
                    CommonBottomBar(
                        onClickBucket = {navigator.push(BucketScreen(db))},
                        onClickFavour = {},
                        onClickHome = {navigator.push(HomeScreen(db))}
                    )
                }
            },
            content = {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(PaddingValues(horizontal = 20.dp))
                        .padding(top = if (screen == ScreenType.CATEGORY) 203.dp else 112.dp)
                        .fillMaxSize()
                        .height(182.dp),
                    contentPadding = PaddingValues(vertical = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    itemsIndexed(viewModel.shoesList) { index, shoes ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CommonShoesCard(
                                shoes = shoes,
                                onAdd = {
                                    viewModel.inBucket(shoes)
                                },
                                onFavourite = {
                                    viewModel.inFavourite(shoes)
                                },
                                onCardClick = {navigator.push(DetailsScreen(db, it))}

                            )
                            if (index < viewModel.shoesList.size - 1) {
                                Spacer(Modifier.width(15.dp))
                            }
                        }
                    }
                }
            }
        )
    }
}
enum class ScreenType {
    POPULAR,
    CATEGORY,
    FAVOURITE
}