package com.example.test12.presentation.orders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.test12.data.local_data_source.AppDatabase
import com.example.test12.presentation.common.CommonOrderShoesCard
import com.example.test12.presentation.common.CommonScaffold
import com.example.test12.presentation.common.CommonTopBar
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

data class OrdersScreen(private val db: AppDatabase): Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { OrdersViewModel() }
        val navigator = LocalNavigator.currentOrThrow
        CommonScaffold(
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(PaddingValues(top = 60.dp, start = 20.dp, end = 20.dp))
                ){
                    CommonTopBar(
                        onBack = {navigator.pop()},
                        label = "Заказы",
                    )
                }
            },
            content = {
                Orders(navigator, viewModel, PaddingValues(top = 108.dp))
            }
        )

    }
    @Composable
    fun Orders(
        navigator: Navigator,
        viewModel: OrdersViewModel,
        paddingValues: PaddingValues
    ){
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
        val yesterdayTime = currentTime.date.minus(1, DateTimeUnit.DAY)
        val state = viewModel.state.collectAsState().value
        val today = state.orders.filter { order ->
            order.order_date.date == currentTime.date
        }
        val yesterday = state.orders.filter { order ->
            order.order_date.date == yesterdayTime
        }
        val others = state.orders.filter { order ->
            order.order_date.date < yesterdayTime
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "Недавние"
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                userScrollEnabled = false
            ) { 
                itemsIndexed(today){index, item ->
                    CommonOrderShoesCard(
                        orderDate = item.order_date.toString(),
                        orderNumber = item.order_number,
                        shoes = item.listShoes.first(),
                        onReorder = {},
                        onDelete = {},
                        modifier = Modifier,
                        onCardClick = {}
                    )
                    if (index < today.size) {
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
            Text(
                "Вчера",
                modifier = Modifier.padding(top = 24.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                userScrollEnabled = false
            ) {
                itemsIndexed(yesterday){index, item ->
                    CommonOrderShoesCard(
                        orderDate = item.order_date.toString(),
                        orderNumber = item.order_number,
                        shoes = item.listShoes.first(),
                        onReorder = {},
                        onDelete = {},
                        modifier = Modifier,
                        onCardClick = {}
                    )
                    if (index < today.size) {
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
            Text(
                "Прошлые",
                modifier = Modifier.padding(top = 24.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                userScrollEnabled = false
            ) {
                itemsIndexed(others){index, item ->
                    CommonOrderShoesCard(
                        orderDate = item.order_date.toString(),
                        orderNumber = item.order_number,
                        shoes = item.listShoes.first(),
                        onReorder = {},
                        onDelete = {},
                        modifier = Modifier,
                        onCardClick = {}
                    )
                    if (index < today.size) {
                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}