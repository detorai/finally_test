package com.example.test12.data.remote_data_source.sign_in

import com.example.test12.data.remote_data_source.SupabaseClient
import com.example.test12.domain.sign_in.request.AuthRequest
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SignInRepository {
    suspend fun auth(authRequest: AuthRequest): Auth{
        SupabaseClient.client.auth.signInWith(Email){
            email = authRequest.email
            password = authRequest.password
        }
        return SupabaseClient.client.auth
    }
    suspend fun signUp(authRequest: AuthRequest): Auth{
        SupabaseClient.client.auth.signUpWith(Email){
            email = authRequest.email
            password = authRequest.password
            data = buildJsonObject {
                put("Display name", "123")
            }
        }
        return SupabaseClient.client.auth
    }
    suspend fun resetPassword(email: String): Auth {
        SupabaseClient.client.auth.resetPasswordForEmail(email)
        return SupabaseClient.client.auth
    }
    suspend fun sendOTP(email: String, token: String): Auth{
        SupabaseClient.client.auth.verifyEmailOtp(OtpType.Email.EMAIL, email, token)
        return SupabaseClient.client.auth
    }
    suspend fun updateUser(password: String): Auth{
        val result = SupabaseClient.client.auth.updateUser{
            this.password = password
        }
        return SupabaseClient.client.auth
    }
}