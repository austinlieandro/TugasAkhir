package com.example.tugasakhir.data.repository

import com.example.tugasakhir.api.response.ResponseInputKaryawan
import com.example.tugasakhir.api.retrofit.ApiService
import com.example.tugasakhir.data.pref.UserPreference

class KaryawanRepository(private val apiService: ApiService, userPreference: UserPreference) {
    suspend fun inputKaryawanBengkel(nama_karyawan: String, bengkels_id: Int): ResponseInputKaryawan{
        return apiService.inputKaryawanbengkel(nama_karyawan, bengkels_id)
    }

    companion object{
        @Volatile
        private var instance: KaryawanRepository? = null
        fun getInstance(apiService: ApiService, userPreference: UserPreference): KaryawanRepository =
            instance ?: synchronized(this){
                instance ?: KaryawanRepository(apiService, userPreference)
            }.also { instance = it }
    }
}