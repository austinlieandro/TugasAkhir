package com.example.tugasakhir.di

import android.content.Context
import com.example.tugasakhir.api.retrofit.ApiConfig
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.example.tugasakhir.data.repository.UserRepository

object Injection {
    fun provideUserRepository(context: Context): UserRepository{
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref, context)
        return UserRepository.getInstance(apiService, pref)
    }
}