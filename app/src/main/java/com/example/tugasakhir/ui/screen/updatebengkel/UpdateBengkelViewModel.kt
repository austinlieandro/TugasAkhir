package com.example.tugasakhir.ui.screen.updatebengkel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.Bengkel
import com.example.tugasakhir.api.response.ReservasiBengkel
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseDisplayReservasiBengkel
import com.example.tugasakhir.api.response.ResponseUpdateBengkel
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UpdateBengkelViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()
    val errorBengkel = MutableLiveData<String?>()
    val statusBengkel: MutableLiveData<Boolean> = MutableLiveData()
    val errorBengkelReservasi = MutableLiveData<String?>()
    val statusBengkelReservasi: MutableLiveData<Boolean> = MutableLiveData()

    val detailBengkel = MutableLiveData<Bengkel?>()

    val reservasiList = MutableLiveData<List<ReservasiBengkel?>?>()

    fun getReservasiBengkel(bengkelId: Int){
        viewModelScope.launch {
            try {
                val reservasiBengkelResponse = repository.displayReservasiBengkel(bengkelId)
                reservasiList.postValue(reservasiBengkelResponse.reservasi)
                statusBengkelReservasi.postValue(true)
                Log.d("RESERVASI BENGKEL", "$reservasiBengkelResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("RESERVASI BENGKEL", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDisplayReservasiBengkel::class.java)
                errorBengkelReservasi.postValue(errorBody.message)
                statusBengkelReservasi.postValue(false)
                Log.d("RESERVASI BENGKEL", "$e")
            }catch (e: Exception) {
                errorBengkelReservasi.postValue("Terjadi kesalahan saat membuat data")
                statusBengkelReservasi.postValue(false)
                Log.d("RESERVASI BENGKEL", "$e")
            }
        }
    }

    fun updateBengkel(
        usersId: Int,
        id: Int,
        nama_bengkel: String,
        lokasi_bengkel: String,
        number_bengkel: String,
        alamat_bengkel: String,
        gmaps_bengkel: String,
        jenis_kendaraan: List<String>,
        hari_operasional: List<String>,
        jam_buka: String,
        jam_tutup: String){
        viewModelScope.launch {
            try {
                val updateBengkelResponse = repository.updateBengkel(
                    usersId,
                    id,
                    nama_bengkel,
                    lokasi_bengkel,
                    number_bengkel,
                    alamat_bengkel,
                    gmaps_bengkel,
                    jenis_kendaraan,
                    hari_operasional,
                    jam_buka,
                    jam_tutup)
                status.postValue(true)
                Log.d("UPDATE BANGKEL", "$updateBengkelResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("UPDATE BENGKEL", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseUpdateBengkel::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("UPDATE BENGKEL", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("UPDATE BENGKEL", "$e")
            }
        }
    }

    fun getDetailBengkel(userdId: Int, id: Int){
        viewModelScope.launch {
            try {
                val detailBengkelResponse = repository.getDetailBengkel(userdId, id)
                detailBengkel.postValue(detailBengkelResponse.bengkel)
                statusBengkel.postValue(true)
                Log.d("DETAIL BENGKEL", "Bengkel: ${detailBengkel.value}")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("DETAIL BENGKEL", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDetailBengkel::class.java)
                errorBengkel.postValue(errorBody.message)
                statusBengkel.postValue(false)
                Log.d("DETAIL BENGKEL", "$e")
            }catch (e: Exception) {
                errorBengkel.postValue("Terjadi kesalahan saat membuat data")
                statusBengkel.postValue(false)
                Log.d("DETAIL BENGKEL", "$e")
            }
        }
    }
}