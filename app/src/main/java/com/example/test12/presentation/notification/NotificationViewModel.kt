package com.example.test12.presentation.notification

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.notification.Notification
import com.example.test12.domain.notification.NotificationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationViewModel: ScreenModel {
    val state = MutableStateFlow(NotificationScreenState())
    val notifUseCase = NotificationUseCase()

    init {
        getNotification()
    }

    fun getNotification(){
        screenModelScope.launch {
            val result = notifUseCase.getNotification()
            result.collect { response ->
                when (response) {
                    is ResponseState.Success<*> -> {
                        state.update {
                            it.copy(notifList = response.data as List<Notification>, isLoading = false)
                        }
                    }
                    is ResponseState.Error -> {
                        state.update {
                            it.copy(error = response.error, isLoading = false)
                        }
                    }
                    is ResponseState.Loading -> {
                        state.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }
}