package com.example.test12.data.remote_data_source.user

import com.example.test12.data.remote_data_source.SupabaseClient
import com.example.test12.domain.sign_in.request.AuthRequest
import com.example.test12.domain.sign_in.request.RegisterRequest
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class UserRepository {

    val userAuth = SupabaseClient.client.auth

    suspend fun getProfile(): UserInfo?{
        SupabaseClient.client.auth.retrieveUserForCurrentSession(true)
        return SupabaseClient.client.auth.currentUserOrNull()
    }

    suspend fun auth(authRequest: AuthRequest) {
        SupabaseClient.client.auth.signInWith(Email) {
            email = authRequest.email
            password = authRequest.password
        }
    }

    suspend fun signUp(registerRequest: RegisterRequest) {
        SupabaseClient.client.auth.signUpWith(Email) {
            email = registerRequest.email
            password = registerRequest.password
            data = buildJsonObject {
                put("user_name", registerRequest.name)
            }
        }
    }


    suspend fun resetPassword(email: String) {
        SupabaseClient.client.auth.resetPasswordForEmail(email)
    }

    suspend fun sendOTP(email: String, token: String) {
        SupabaseClient.client.auth.verifyEmailOtp(OtpType.Email.EMAIL, email, token)
    }

    suspend fun updateUser(password: String) {
        SupabaseClient.client.auth.updateUser {
            this.password = password
        }
    }

    suspend fun resendOTP(email: String) {
        SupabaseClient.client.auth.resendEmail(OtpType.Email.EMAIL, email)
    }
}