package com.example.tugasakhir.ui.screen.daftarbengkel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.DaftarBengkel
import com.example.tugasakhir.api.response.ResponseDaftarBengkel
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.repository.BengkelRepository
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class DaftarBengkelViewModel(private val repository: BengkelRepository, private val repositoryUser: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val daftarBengkel = MutableLiveData<DaftarBengkel?>()

    fun daftarBengkel(nama_bengkel: String, lokasi_bengkel: String, number_bengkel: String, alamat_bengkel: String, gmaps_bengkel: String, jenis_kendaraan: List<String>, jenis_layanan: List<String>, hari_operasional: List<String>, jam_buka: String, jam_tutup: String, users_id: Int){
        viewModelScope.launch {
            try {
                val daftarBengkelResponse = repository.daftarBengkel(nama_bengkel, lokasi_bengkel, number_bengkel, alamat_bengkel, gmaps_bengkel, jenis_kendaraan, jenis_layanan, hari_operasional, jam_buka, jam_tutup, users_id)
                val idUser = daftarBengkelResponse.user?.id
                val statusUser = daftarBengkelResponse.user?.userBengkel
                val usernameUser = daftarBengkelResponse.user?.username
                val idBengkelUser = daftarBengkelResponse.bengkel?.id
                daftarBengkel.postValue(daftarBengkelResponse.bengkel)
                status.postValue(true)
                runBlocking { repositoryUser.logout() }
                runBlocking { repositoryUser.saveSession(UserModel(usernameUser ?: "", true, idUser ?: 0, statusUser ?: "", idBengkelUser ?: 0)) }
                Log.d("DAFTAR BENGKEL", "$daftarBengkelResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("DAFTAR BENGKEL", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDaftarBengkel::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("DAFTAR BENGKEL", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("DAFTAR BENGKEL", "$e")
            }
        }
    }
}