package com.example.tugasakhir.di

import android.content.Context
import com.example.tugasakhir.api.retrofit.ApiConfig
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.example.tugasakhir.data.repository.BengkelRepository
import com.example.tugasakhir.data.repository.KaryawanRepository
import com.example.tugasakhir.data.repository.RiwayatRepository
import com.example.tugasakhir.data.repository.UserRepository

object Injection {
    fun provideUserRepository(context: Context): UserRepository{
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref, context)
        return UserRepository.getInstance(apiService, pref)
    }

    fun provideBengkelRepository(context: Context): BengkelRepository{
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref, context)
        return BengkelRepository.getInstance(apiService, pref)
    }

    fun provideRiwayatRepository(context: Context): RiwayatRepository{
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref, context)
        return RiwayatRepository.getInstance(apiService, pref)
    }

    fun provideKaryawanRepository(context: Context): KaryawanRepository{
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref, context)
        return KaryawanRepository.getInstance(apiService, pref)
    }
}