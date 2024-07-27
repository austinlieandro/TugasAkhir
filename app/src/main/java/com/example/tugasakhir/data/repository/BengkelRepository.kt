package com.example.tugasakhir.data.repository

import com.example.tugasakhir.api.response.BengkelItem
import com.example.tugasakhir.api.response.ResponseAsignKaryawan
import com.example.tugasakhir.api.response.ResponseDaftarBengkel
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseDetailJenisLayanan
import com.example.tugasakhir.api.response.ResponseDetailReservasiBengkel
import com.example.tugasakhir.api.response.ResponseDisplayBengkel
import com.example.tugasakhir.api.response.ResponseDisplayPrioritas
import com.example.tugasakhir.api.response.ResponseDisplayPrioritasHarga
import com.example.tugasakhir.api.response.ResponseDisplayReservasiBengkel
import com.example.tugasakhir.api.response.ResponseInputJamOperasionalSatuan
import com.example.tugasakhir.api.response.ResponseInputJenisLayanan
import com.example.tugasakhir.api.response.ResponseInputPrioritasSatuan
import com.example.tugasakhir.api.response.ResponseInputPriortiasHarga
import com.example.tugasakhir.api.response.ResponseJamOperasionalBengkel
import com.example.tugasakhir.api.response.ResponseJenisLayanan
import com.example.tugasakhir.api.response.ResponseReservasiBengkel
import com.example.tugasakhir.api.response.ResponseUpdateBengkel
import com.example.tugasakhir.api.response.ResponseUpdateJamOperasional
import com.example.tugasakhir.api.response.ResponseUpdateJenisLayanan
import com.example.tugasakhir.api.response.ResponseUpdatePrioritas
import com.example.tugasakhir.api.response.ResponseUpdatePrioritasHarga
import com.example.tugasakhir.api.retrofit.ApiService
import com.example.tugasakhir.data.pref.UserPreference

class BengkelRepository(private val apiService: ApiService, private val userPreference: UserPreference) {
    suspend fun getBengkel(): ResponseDisplayBengkel{
        return apiService.displayBengkel()
    }

    suspend fun getDetailBengkel(idUser: Int, id: Int): ResponseDetailBengkel {
        return apiService.detailBengkel(idUser, id)
    }

    suspend fun reservasiBengkel(tanggal_reservasi: String, jam_reservasi: String, jeniskendala_reservasi: String, detail_reservasi: String, kendaraan_reservasi: String, bengkels_id: Int, users_id: Int, kendaraan_id: Int): ResponseReservasiBengkel{
        return apiService.reservasiBengkel(tanggal_reservasi, jam_reservasi, jeniskendala_reservasi, detail_reservasi, kendaraan_reservasi, bengkels_id, users_id, kendaraan_id)
    }

    suspend fun daftarBengkel(
        nama_bengkel: String,
        lokasi_bengkel: String,
        number_bengkel: String,
        alamat_bengkel: String,
        gmaps_bengkel: String,
        jenis_kendaraan: List<String>,
//      jenis_layanan: List<String>,
        hari_operasional: List<String>,
        jam_buka: String,
        jam_tutup: String,
        users_id: Int
    ): ResponseDaftarBengkel{
        val requestBody = HashMap<String, Any>()
        requestBody["nama_bengkel"] = nama_bengkel
        requestBody["lokasi_bengkel"] = lokasi_bengkel
        requestBody["number_bengkel"] = number_bengkel
        requestBody["alamat_bengkel"] = alamat_bengkel
        requestBody["gmaps_bengkel"] = gmaps_bengkel
        requestBody["jenis_kendaraan"] = jenis_kendaraan
//        requestBody["jenis_layanan"] = jenis_layanan
        requestBody["hari_operasional"] = hari_operasional
        requestBody["jam_buka"] = jam_buka
        requestBody["jam_tutup"] = jam_tutup
        requestBody["users_id"] = users_id
        return apiService.daftarBengkel(requestBody)
    }

    suspend fun updateBengkel(
        usersId: Int,
        id: Int,
        nama_bengkel: String,
        lokasi_bengkel: String,
        number_bengkel: String,
        alamat_bengkel: String,
        gmaps_bengkel: String,
        jenis_kendaraan: List<String>,
//                              jenis_layanan: List<String>,
        hari_operasional: List<String>,
        jam_buka: String,
        jam_tutup: String
    ): ResponseUpdateBengkel{
        val requestBody = HashMap<Any, Any>()
        requestBody["nama_bengkel"] = nama_bengkel
        requestBody["lokasi_bengkel"] = lokasi_bengkel
        requestBody["number_bengkel"] = number_bengkel
        requestBody["alamat_bengkel"] = alamat_bengkel
        requestBody["gmaps_bengkel"] = gmaps_bengkel
        requestBody["jenis_kendaraan"] = jenis_kendaraan
//        requestBody["jenis_layanan"] = jenis_layanan
        requestBody["hari_operasional"] = hari_operasional
        requestBody["jam_buka"] = jam_buka
        requestBody["jam_tutup"] = jam_tutup
        return apiService.updateBengkel(usersId, id, requestBody)
    }

    suspend fun daftarJamOperasional(jam_operasional: List<String>, hari_operasional: List<String>, slot: List<Int>, bengkels_id: Int): ResponseJamOperasionalBengkel{
        val requestBody = HashMap<String, Any>()
        requestBody["jam_operasional"] = jam_operasional
        requestBody["hari_operasional"] = hari_operasional
        requestBody["slot"] = slot
        requestBody["bengkels_id"] = bengkels_id
        return apiService.daftarJamOperasional(requestBody)
    }

    suspend fun displayReservasiBengkel(bengkelId: Int): ResponseDisplayReservasiBengkel{
        return apiService.displayReservasiBengkel(bengkelId)
    }

    suspend fun detailReservasiBengkel(id: Int): ResponseDetailReservasiBengkel{
        return apiService.detailReservasiBengkel(id)
    }

    suspend fun assignReservasi(id: Int, karyawan_id: Int, status_reservasi: String): ResponseAsignKaryawan{
        return apiService.assignKaryawan(id, karyawan_id, status_reservasi)
    }

    suspend fun updateJamOperasional(bengkelId: Int, id: Int, jam_operasional: String, slot: Int): ResponseUpdateJamOperasional{
        return apiService.updateJamOperasional(bengkelId, id, jam_operasional, slot)
    }

    suspend fun searchBengkel(query: String): List<BengkelItem?>?{
        return apiService.displayBengkel().bengkel?.filter {
            it?.namaBengkel?.contains(query, ignoreCase = true) == true
        }
    }

    suspend fun jenisKendaraan(jenisKendaraan: String): List<BengkelItem?>? {
        return apiService.displayBengkel().bengkel?.filter {
            val matchesJenisKendaraan = when (jenisKendaraan) {
                "mobil" -> it?.jenisKendaraan?.any { jenis -> jenis?.contains("mobil", ignoreCase = true) == true } == true
                "motor" -> it?.jenisKendaraan?.any { jenis -> jenis?.contains("motor", ignoreCase = true) == true } == true
                else -> true
            }
            matchesJenisKendaraan
        }
    }

    suspend fun displayPrioritas(idBengkel: Int): ResponseDisplayPrioritas{
        return apiService.displayPrioritas(idBengkel)
    }

    suspend fun displayPrioritasHarga(idBengkel: Int): ResponseDisplayPrioritasHarga{
        return apiService.displayPrioritasHarga(idBengkel)
    }

    suspend fun inputJamOperasionalSatuan(jam_operasional: String, hari_operasional: String, slot: Int, bengkels_id: Int): ResponseInputJamOperasionalSatuan{
        return apiService.inputJamOperasionalSatuan(jam_operasional, hari_operasional, slot, bengkels_id)
    }

    suspend fun inputJenisLayanan(
        nama_layanan: String,
        jenis_layanan: List<String>,
        harga_layanan: Int,
        bengkels_id: Int
    ):ResponseInputJenisLayanan{
        val requestBody = HashMap<String, Any>()
        requestBody["nama_layanan"] = nama_layanan
        requestBody["jenis_layanan"] = jenis_layanan
        requestBody["harga_layanan"] = harga_layanan
        requestBody["bengkels_id"] = bengkels_id
        return apiService.inputJenisLayanan(requestBody)
    }

    suspend fun inputPrioritasHarga(
        harga: List<Int>,
        bobot_nilai: List<Int>,
        bengkels_id: Int
    ): ResponseInputPriortiasHarga{
        val requestBody = HashMap<Any, Any>()
        requestBody["harga"] = harga
        requestBody["bobot_nilai"] = bobot_nilai
        requestBody["bengkels_id"] = bengkels_id
        return apiService.inputPrioritasHarga(requestBody)
    }

    suspend fun getJenisLayanan(bengkels_id: Int): ResponseJenisLayanan{
        return apiService.getJenisLayanan(bengkels_id)
    }

    suspend fun updateJenisLayanan(
        bengkelId: Int,
        id: Int,
        nama_layanan: String,
        jenis_layanan: List<String>,
        harga_layanan: Int
    ): ResponseUpdateJenisLayanan{
        val requestBody = HashMap<Any, Any>()
        requestBody["nama_layanan"] = nama_layanan
        requestBody["jenis_layanan"] = jenis_layanan
        requestBody["harga_layanan"] = harga_layanan
        return apiService.updateJenisLayanan(bengkelId, id, requestBody)
    }

    suspend fun detailJenisLayanan(id: Int): ResponseDetailJenisLayanan{
        return apiService.detailJenisLayanan(id)
    }

    suspend fun updatePrioritasHarga(bengkelsId: Int, id: Int, harga: Int, bobot_nilai: Int): ResponseUpdatePrioritasHarga {
        return apiService.updatePrioritasHarga(bengkelsId, id, harga, bobot_nilai)
    }

    suspend fun inputPrioritasSatuan(jenis_kendaraan: String, jenis_kerusakan: String, bobot_nilai: Int, bobot_estimasi: Int, bobot_urgensi: Int, bengkels_id: Int): ResponseInputPrioritasSatuan{
        return apiService.inputPrioritasSatuan(jenis_kendaraan, jenis_kerusakan, bobot_nilai, bobot_estimasi, bobot_urgensi, bengkels_id)
    }

    suspend fun updatePrioritas(bengkelsId: Int, id: Int, bobot_nilai: Int, bobot_estimasi: Int, bobot_urgensi: Int): ResponseUpdatePrioritas {
        return apiService.updatePrioritas(bengkelsId, id, bobot_nilai, bobot_estimasi, bobot_urgensi)
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