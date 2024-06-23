package com.example.tugasakhir.data.repository

import android.util.Log
import com.example.tugasakhir.api.response.ResponseDaftarBengkel
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

    suspend fun daftarBengkel(nama_bengkel: String, lokasi_bengkel: String, number_bengkel: String, alamat_bengkel: String, gmaps_bengkel: String, jenis_kendaraan: List<String>, jenis_layanan: List<String>, hari_operasional: List<String>, jam_buka: String, jam_tutup: String, users_id: Int): ResponseDaftarBengkel{
        val requestBody = HashMap<String, Any>()
        requestBody["nama_bengkel"] = nama_bengkel
        requestBody["lokasi_bengkel"] = lokasi_bengkel
        requestBody["number_bengkel"] = number_bengkel
        requestBody["alamat_bengkel"] = alamat_bengkel
        requestBody["gmaps_bengkel"] = gmaps_bengkel
        requestBody["jenis_kendaraan"] = jenis_kendaraan
        requestBody["jenis_layanan"] = jenis_layanan
        requestBody["hari_operasional"] = hari_operasional
        requestBody["jam_buka"] = jam_buka
        requestBody["jam_tutup"] = jam_tutup
        requestBody["users_id"] = users_id
        return apiService.daftarBengkel(requestBody)
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