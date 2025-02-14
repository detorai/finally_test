package com.example.test12.presentation.orders

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.domain.orders.Order
import com.example.test12.domain.shoes.Shoes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

class OrdersViewModel(): ScreenModel {
    val state = MutableStateFlow(OrdersScreenState())

    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    val yesterday = LocalDateTime(currentTime.date.minus(1, DateTimeUnit.DAY), currentTime.time)
    val others = LocalDateTime(currentTime.date.minus(4, DateTimeUnit.DAY), currentTime.time)
    val shoesList =
        listOf<Shoes>(
            Shoes(
                id = 1,
                name= "123",
                cost = 12412.12,
                count = 1,
                description = "1215212asc",
                isPopular = false,
                isFavourite = false,
                inBucket = false,
                image = "https://static.gettyimages.com/display-sets/creative-landing/images/GettyImages-2181662163.jpg",
                category = 2
            ),
            Shoes(id = 2,
                name= "123",
                cost = 12412.12,
                count = 1,
                description = "1215212asc",
                isPopular = false,
                isFavourite = false,
                inBucket = false,
                image = "https://static.gettyimages.com/display-sets/creative-landing/images/GettyImages-2181662163.jpg",
                category = 2),
            Shoes(id = 3,
                name= "123",
                cost = 12412.12,
                count = 1,
                description = "1215212asc",
                isPopular = false,
                isFavourite = false,
                inBucket = false,
                image = "https://static.gettyimages.com/display-sets/creative-landing/images/GettyImages-2181662163.jpg",
                category = 2),
            Shoes(id = 4,
                name= "123",
                cost = 12412.12,
                count = 1,
                description = "1215212asc",
                isPopular = false,
                isFavourite = false,
                inBucket = false,
                image = "https://static.gettyimages.com/display-sets/creative-landing/images/GettyImages-2181662163.jpg",
                category = 2),
        )
    val list1 = listOf<Order>(
        Order(
            listShoes = shoesList,
            order_number = "123124",
            order_date = yesterday,
            order_name = "123124",
            user_email = "wqerqw",
            user_addres = "1231245",
            user_number = "214125",
            walletCard = "125125"
        ),
        Order(
            listShoes = shoesList,
            order_number = "123124",
            order_date = currentTime,
            order_name = "123124",
            user_email = "wqerqw",
            user_addres = "1231245",
            user_number = "214125",
            walletCard = "125125"
        ),
        Order(
            listShoes = shoesList,
            order_number = "123124",
            order_date = others,
            order_name = "123124",
            user_email = "wqerqw",
            user_addres = "1231245",
            user_number = "214125",
            walletCard = "125125"
        ),
        Order(
            listShoes = shoesList,
            order_number = "123124",
            order_date = currentTime,
            order_name = "123124",
            user_email = "wqerqw",
            user_addres = "1231245",
            user_number = "214125",
            walletCard = "125125"
        ),
    )

    init {
        screenModelScope.launch {
            state.update {
                it.copy(
                    orders = list1
                )
            }
        }
    }

}