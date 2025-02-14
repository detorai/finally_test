package com.example.test12.domain.notification

import com.example.test12.data.remote_data_source.notification.NotificationRepository
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.sales.Sales
import kotlinx.coroutines.flow.flow

class NotificationUseCase {
    val repository = NotificationRepository()

    suspend fun getNotification() = flow<ResponseState>{
        return@flow try {
            emit(ResponseState.Loading())
            val result = repository.getNotification().map { notif ->
                Notification(
                    id = notif.id,
                    label = notif.notif_label,
                    content = notif.notif_content,
                    time = notif.notif_time
                )
            }
            emit(ResponseState.Success(data = result))
        } catch (e:Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
}