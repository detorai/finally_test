package com.example.test12.domain.sign_in

import com.example.test12.data.remote_data_source.user.UserRepository
import com.example.test12.domain.common.ResponseState
import com.example.test12.domain.sign_in.request.AuthRequest
import com.example.test12.domain.sign_in.request.RegisterRequest
import kotlinx.coroutines.flow.flow

class UserUseCase {
    val repository = UserRepository()
    val userAuth = repository.userAuth

    suspend fun getProfile() = repository.getProfile()

    suspend fun auth(authRequest: AuthRequest) = flow<ResponseState> {
        return@flow try {
            emit(ResponseState.Loading())
            val result = repository.auth(authRequest)
            emit(ResponseState.Success(true))
        } catch (e: Exception){
            emit(ResponseState.Error(e.message.toString()))
        }
    }
    suspend fun signUp(registerRequest: RegisterRequest) = flow<ResponseState> {
        return@flow try {
            emit(ResponseState.Loading())
            val result = repository.signUp(registerRequest)
            emit(ResponseState.Success(true))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    suspend fun resetPassword(email: String)= flow<ResponseState> {
        return@flow try {
            emit(ResponseState.Loading())
            val result = repository.resetPassword(email)
            emit(ResponseState.Success(true))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }
    suspend fun sendOTP(email: String, token: String)= flow<ResponseState>{
        return@flow try {
            emit(ResponseState.Loading())
            val result = repository.sendOTP(email, token)
            emit(ResponseState.Success(true))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }
    suspend fun updateUser(password: String)= flow<ResponseState>{
        return@flow try {
            emit(ResponseState.Loading())
            val result = repository.updateUser(password)
            emit(ResponseState.Success(true))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }
    suspend fun signOut()= flow<ResponseState>{
        return@flow try {
            emit(ResponseState.Loading())
            val result = repository.signOut()
            emit(ResponseState.Success(true))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }
}