package com.example.tugasakhir.ui.screen.bengkeldetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.Bengkel
import com.example.tugasakhir.api.response.JamOperasionalItem
import com.example.tugasakhir.api.response.JenisLayananItem
import com.example.tugasakhir.api.response.KendaraanItem
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseDisplayKendaraan
import com.example.tugasakhir.api.response.ResponseReservasiBengkel
import com.example.tugasakhir.api.response.ResponseTogleFavorit
import com.example.tugasakhir.data.repository.BengkelRepository
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class BengkelDetailViewModel(private val repository: BengkelRepository, private val repositoryUser: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()
    val errorReservasi = MutableLiveData<String?>()
    val statusReservasi: MutableLiveData<Boolean> = MutableLiveData()
    val errorTogle = MutableLiveData<String?>()
    val statusTogle: MutableLiveData<Boolean> = MutableLiveData()
    val errorKendaraan = MutableLiveData<String?>()
    val statusKendaraan: MutableLiveData<Boolean> = MutableLiveData()

    val detailBengkel = MutableLiveData<Bengkel?>()
    val jenisLayananBengkel = MutableLiveData<List<JenisLayananItem?>?>()
    val jamOperasionalList = MutableLiveData<List<JamOperasionalItem?>?>()
    val statusFavorit = MutableLiveData<String?>()

    val kendaraanList = MutableLiveData<List<KendaraanItem?>?>()

    fun getDetailBengkel(idUser: Int, id: Int){
        viewModelScope.launch {
            try {
                val detailBengkelResponse = repository.getDetailBengkel(idUser, id)
                detailBengkel.postValue(detailBengkelResponse.bengkel)
                jamOperasionalList.postValue(detailBengkelResponse.jamOperasional)
                jenisLayananBengkel.postValue(detailBengkelResponse.jenisLayanan)
                statusFavorit.postValue(detailBengkelResponse.statusFavorit)
                status.postValue(true)
                Log.d("DETAIL BENGKEL", "Bengkel: ${detailBengkel.value} Jam Operasional: ${jamOperasionalList.value}")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("DETAIL BENGKEL", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDetailBengkel::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("DETAIL BENGKEL", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("DETAIL BENGKEL", "$e")
            }
        }
    }

    fun reservasiBengkel(tanggal_reservasi: String, jam_reservasi: String, jeniskendala_reservasi: String, detail_reservasi: String, kendaraan_reservasi: String, bengkels_id: Int, users_id: Int, kendaraan_id: Int){
        viewModelScope.launch {
            try {
                val reservasiResponse = repository.reservasiBengkel(tanggal_reservasi, jam_reservasi, jeniskendala_reservasi, detail_reservasi, kendaraan_reservasi, bengkels_id, users_id, kendaraan_id)
                statusReservasi.postValue(true)
                Log.d("RESERVASI", "$reservasiResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ResponseReservasiBengkel::class.java)
                val errorMessage = errorBody?.message ?: "Terjadi kesalahan saat reservasi"
                errorReservasi.postValue(errorMessage)
                statusReservasi.postValue(false)
                Log.d("RESERVASI", "$e")
            } catch (e: Exception) {
                errorReservasi.postValue("Terjadi kesalahan saat reservasi")
                statusReservasi.postValue(false)
                Log.d("RESERVASI", "$e")
            }
        }
    }

    fun togleFavorit(idUser: Int, idBengkel: Int){
        viewModelScope.launch {
            try {
                val togleFavoritResponse = repositoryUser.togleFavorite(idUser, idBengkel)
                statusTogle.postValue(true)
                Log.d("TOGLE FAVORIT", "$togleFavoritResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ResponseTogleFavorit::class.java)
                val errorMessage = errorBody?.message ?: "Terjadi kesalahan saat reservasi"
                errorTogle.postValue(errorMessage)
                statusTogle.postValue(false)
                Log.d("TOGLE FAVORIT", "$e")
            } catch (e: Exception) {
                errorTogle.postValue("Terjadi kesalahan saat reservasi")
                statusTogle.postValue(false)
                Log.d("TOGLE FAVORIT", "$e")
            }
        }
    }

    fun getKendaraan(id: Int){
        viewModelScope.launch {
            try {
                val kendaraanResponse = repositoryUser.displayKendaraan(id)
                kendaraanList.postValue(kendaraanResponse.kendaraan)
                statusKendaraan.postValue(true)
                Log.d("KENDARAAN", "$kendaraanResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("KENDARAAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDisplayKendaraan::class.java)
                errorKendaraan.postValue(errorBody.message)
                statusKendaraan.postValue(false)
                Log.d("KENDARAAN", "$e")
            }catch (e: Exception) {
                errorKendaraan.postValue("Terjadi kesalahan saat membuat data")
                statusKendaraan.postValue(false)
                Log.d("KENDARAAN", "$e")
            }
        }
    }
}