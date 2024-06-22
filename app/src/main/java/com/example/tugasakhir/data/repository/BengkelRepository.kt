package com.example.tugasakhir.data.repository

import android.util.Log
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseDisplayBengkel
import com.example.tugasakhir.api.response.ResponseReservasiBengkel
import com.example.tugasakhir.api.retrofit.ApiService
import com.example.tugasakhir.data.pref.UserPreference

class BengkelRepository(private val apiService: ApiService, private val userPreference: UserPreference) {
    suspend fun getBengkel(): ResponseDisplayBengkel{
        return apiService.displayBengkel()
    }

    suspend fun getDetailBengkel(id: Int): ResponseDetailBengkel {
        return apiService.detailBengkel(id)
    }

    suspend fun reservasiBengkel(tanggal_reservasi: String, jam_reservasi: String, jeniskendala_reservasi: String, detail_reservasi: String, kendaraan_reservasi: String, bengkels_id: Int, users_id: Int): ResponseReservasiBengkel{
        Log.d("DEBUG", "$tanggal_reservasi, $jam_reservasi, $jeniskendala_reservasi, $detail_reservasi, $kendaraan_reservasi, $bengkels_id, $users_id")
        return apiService.reservasiBengkel(tanggal_reservasi, jam_reservasi, jeniskendala_reservasi, detail_reservasi, kendaraan_reservasi, bengkels_id, users_id)
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