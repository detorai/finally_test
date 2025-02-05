package com.example.test12.domain.sign_in

import com.example.test12.data.remote_data_source.sign_in.SignInRepository
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.sign_in.request.AuthRequest
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.flow

class SignInUseCase {
    val repository = SignInRepository()

    suspend fun auth(authRequest: AuthRequest) = flow<ResponseState> {
        return@flow try {
            emit(ResponseState.Loading())
            val result = repository.auth(authRequest)
            result.sessionStatus.collect{status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        emit(ResponseState.Success(data = result))
                    }
                    is SessionStatus.NotAuthenticated -> {
                        emit(ResponseState.Error(error = "Not Auth"))
                    }
                    is SessionStatus.RefreshFailure -> {
                        emit(ResponseState.Error(error = "refresh failure"))
                    }
                    is SessionStatus.Initializing -> {
                        emit(ResponseState.Loading())
                    }
                }
            }
        } catch (e: Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
}