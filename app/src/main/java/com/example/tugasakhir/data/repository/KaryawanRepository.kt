package com.example.tugasakhir.data.repository

import com.example.tugasakhir.api.response.ResponseDeleteKaryawan
import com.example.tugasakhir.api.response.ResponseDisplayKaryawan
import com.example.tugasakhir.api.response.ResponseInputKaryawan
import com.example.tugasakhir.api.response.ResponseUpdateKaryawan
import com.example.tugasakhir.api.retrofit.ApiService
import com.example.tugasakhir.data.pref.UserPreference

class KaryawanRepository(private val apiService: ApiService, userPreference: UserPreference) {
    suspend fun inputKaryawanBengkel(nama_karyawan: String, bengkels_id: Int): ResponseInputKaryawan{
        return apiService.inputKaryawanbengkel(nama_karyawan, bengkels_id)
    }

    suspend fun displayKaryawan(id: Int): ResponseDisplayKaryawan{
        return apiService.displayKaryawan(id)
    }

    suspend fun updateKaryawan(bengkelId: Int, id: Int, nama_karyawan: String): ResponseUpdateKaryawan{
        return apiService.updateKaryawan(bengkelId, id, nama_karyawan)
    }

    suspend fun deleteKaryawan(bengkelId: Int, id: Int): ResponseDeleteKaryawan{
        return apiService.deleteKaryawan(bengkelId, id)
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