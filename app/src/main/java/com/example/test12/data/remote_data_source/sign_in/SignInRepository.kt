package com.example.test12.data.remote_data_source.sign_in

import com.example.test12.data.remote_data_source.SupabaseClient
import com.example.test12.domain.sign_in.request.AuthRequest
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

class SignInRepository {
    suspend fun auth(authRequest: AuthRequest): Auth{
        SupabaseClient.client.auth.signInWith(Email){
            email = authRequest.email
            password = authRequest.password
        }
        return SupabaseClient.client.auth
    }
}