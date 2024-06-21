package com.example.tugasakhir.data.repository

import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseDisplayBengkel
import com.example.tugasakhir.api.retrofit.ApiService
import com.example.tugasakhir.data.pref.UserPreference

class BengkelRepository(private val apiService: ApiService, private val userPreference: UserPreference) {
    suspend fun getBengkel(): ResponseDisplayBengkel{
        return apiService.displayBengkel()
    }

    suspend fun getDetailBengkel(id: Int): ResponseDetailBengkel {
        return apiService.detailBengkel(id)
    }

    companion object{
        @Volatile
        private var instance: BengkelRepository? = null
        fun getInstance(apiService: ApiService, userPreference: UserPreference): BengkelRepository =
            instance ?: synchronized(this){
                instance ?: BengkelRepository(apiService, userPreference)
            }.also { instance = it }
    }
}