package com.example.tugasakhir.data.repository

import com.example.tugasakhir.api.response.ResponseRiwayatReservasi
import com.example.tugasakhir.api.retrofit.ApiService
import com.example.tugasakhir.data.pref.UserPreference

class RiwayatRepository(private val apiService: ApiService, private val userPreference: UserPreference) {
    suspend fun getRiwayatReservasi(id: Int): ResponseRiwayatReservasi {
        return apiService.riwayatReservasi(id)
    }

    companion object{
        @Volatile
        private var instance: RiwayatRepository? = null
        fun getInstance(apiService: ApiService, userPreference: UserPreference): RiwayatRepository =
            instance ?: synchronized(this){
                instance ?: RiwayatRepository(apiService, userPreference)
            }.also { instance = it }
    }
}