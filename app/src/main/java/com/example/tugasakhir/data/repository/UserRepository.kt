package com.example.tugasakhir.data.repository

import com.example.tugasakhir.api.response.ResponseDisplayKendaraan
import com.example.tugasakhir.api.response.ResponseLogin
import com.example.tugasakhir.api.response.ResponseProfile
import com.example.tugasakhir.api.response.ResponseRegister
import com.example.tugasakhir.api.retrofit.ApiService
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class UserRepository(private val apiService: ApiService, private val userPreference: UserPreference) {
    suspend fun register(name: String, phone: String, email: String, username: String, passowrd: String): ResponseRegister {
        return apiService.register(name, phone, email, username, passowrd)
    }

    suspend fun login(username: String, password: String): ResponseLogin{
        return apiService.login(username, password)
    }

    suspend fun displayKendaraan(id: Int): ResponseDisplayKendaraan{
        return apiService.displayKendaraan(id)
    }

    suspend fun profile(id: Int): ResponseProfile{
        return apiService.profile(id)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object{
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService, userPreference: UserPreference): UserRepository =
            instance ?: synchronized(this){
                instance ?: UserRepository(apiService, userPreference)
            }.also { instance = it }
    }
}