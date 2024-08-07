package com.example.tugasakhir.data.repository

import com.example.tugasakhir.api.response.ResponseDeleteKendaraan
import com.example.tugasakhir.api.response.ResponseDetailKendaraan
import com.example.tugasakhir.api.response.ResponseDisplayJenisService
import com.example.tugasakhir.api.response.ResponseDisplayKendaraan
import com.example.tugasakhir.api.response.ResponseDisplayMerekKendaraan
import com.example.tugasakhir.api.response.ResponseFavoritBengkel
import com.example.tugasakhir.api.response.ResponseInputJenisService
import com.example.tugasakhir.api.response.ResponseInputKendaraan
import com.example.tugasakhir.api.response.ResponseInputMerekKendaraan
import com.example.tugasakhir.api.response.ResponseLogin
import com.example.tugasakhir.api.response.ResponseProfile
import com.example.tugasakhir.api.response.ResponseRegister
import com.example.tugasakhir.api.response.ResponseTogleFavorit
import com.example.tugasakhir.api.response.ResponseUpdateJenisService
import com.example.tugasakhir.api.response.ResponseUpdateKendaraan
import com.example.tugasakhir.api.response.ResponseUpdateMerekKendaraan
import com.example.tugasakhir.api.response.ResponseUpdateProfile
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

    suspend fun inputKendaraan(jenis_kendaraan: String, plat_kendaraan: String, users_id: Int, merek_kendaraan_id: Int): ResponseInputKendaraan {
        return apiService.inputKendaraan(jenis_kendaraan, plat_kendaraan, users_id, merek_kendaraan_id)
    }

    suspend fun detailKendaraan(usersId: Int, kendaraan_id: Int): ResponseDetailKendaraan{
        return apiService.detailKendaraan(usersId, kendaraan_id)
    }

    suspend fun updateKendaraan(usersId: Int, kendaraan_id: Int, plat_kendaraan: String, merek_kendaraan_id: Int): ResponseUpdateKendaraan{
        return apiService.updateKendaraan(usersId, kendaraan_id, plat_kendaraan, merek_kendaraan_id)
    }

    suspend fun deleteKendaraan(usersId: Int, kendaraan_id: Int): ResponseDeleteKendaraan{
        return apiService.deleteKendaraan(usersId, kendaraan_id)
    }

    suspend fun favoriteBengkel(id: Int): ResponseFavoritBengkel{
        return apiService.favoriteBengkel(id)
    }

    suspend fun updateProfile(id: Int, name: String, email: String, username: String, password: String, phone: String): ResponseUpdateProfile{
        return apiService.updateProfile(id, name, email, username, password, phone)
    }

    suspend fun togleFavorite(users_id: Int, bengkels_id: Int): ResponseTogleFavorit{
        return apiService.togleFavorite(users_id, bengkels_id)
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

    suspend fun inputMerekKendaraan(jenis_kendaraan: String, merek_kendaraan: String, users_id: Int): ResponseInputMerekKendaraan{
        return apiService.inputMerekKendaraan(jenis_kendaraan, merek_kendaraan, users_id)
    }

    suspend fun displayMerekKendaraan(): ResponseDisplayMerekKendaraan{
        return apiService.displayMerekKendaraan()
    }

    suspend fun updateMerekKendaraan(usersId: Int, merekKendaraanId: Int, merek_kendaraan: String): ResponseUpdateMerekKendaraan{
        return apiService.updateMerekKendaraan(usersId, merekKendaraanId, merek_kendaraan)
    }

    suspend fun inputJenisService(nama_service: String, users_id: Int): ResponseInputJenisService{
        return apiService.inputJenisService(nama_service, users_id)
    }

    suspend fun displayJenisService(): ResponseDisplayJenisService {
        return apiService.displayJenisService()
    }

    suspend fun updateJenisService(usersId: Int, serviceId: Int, nama_service: String): ResponseUpdateJenisService {
        return apiService.updateJenisService(usersId, serviceId, nama_service)
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