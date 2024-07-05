package com.example.tugasakhir.ui.screen.detailreservasibengkel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.KaryawanItem
import com.example.tugasakhir.api.response.Reservasi
import com.example.tugasakhir.api.response.ResponseAsignKaryawan
import com.example.tugasakhir.api.response.ResponseDetailReservasiBengkel
import com.example.tugasakhir.api.response.ResponseDisplayKaryawan
import com.example.tugasakhir.data.repository.BengkelRepository
import com.example.tugasakhir.data.repository.KaryawanRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailReservasiBengkelViewModel(private val repository: BengkelRepository, private val repositoryKaryawan: KaryawanRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val errorKaryawan = MutableLiveData<String?>()
    val statusKaryawan: MutableLiveData<Boolean> = MutableLiveData()

    val errorAssign = MutableLiveData<String?>()
    val statusAssign: MutableLiveData<Boolean> = MutableLiveData()

    val detailReservasi = MutableLiveData<Reservasi?>()

    val karyawanList = MutableLiveData<List<KaryawanItem?>?>()

    fun assignReservasi(id: Int, karyawan_id: Int, status_reservasi: String){
        viewModelScope.launch {
            try {
                val assignReservasiResponse = repository.assignReservasi(id, karyawan_id, status_reservasi)
                statusAssign.postValue(true)
                Log.d("ASSIGN RESERVASI", "$assignReservasiResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("ASSIGN RESERVASI", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseAsignKaryawan::class.java)
                errorAssign.postValue(errorBody.message)
                statusAssign.postValue(false)
                Log.d("ASSIGN RESERVASI", "$e")
            }catch (e: Exception) {
                errorAssign.postValue("Terjadi kesalahan saat membuat data")
                statusAssign.postValue(false)
                Log.d("ASSIGN RESERVASI", "$e")
            }
        }
    }

    fun getKaryawan(id: Int){
        viewModelScope.launch {
            try {
                val karyawanResponse = repositoryKaryawan.displayKaryawan(id)
                karyawanList.postValue(karyawanResponse.karyawan)
                statusKaryawan.postValue(true)
                Log.d("KARYAWAN", "$karyawanResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("KARYAWAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDisplayKaryawan::class.java)
                errorKaryawan.postValue(errorBody.message)
                statusKaryawan.postValue(false)
                Log.d("KARYAWAN", "$e")
            }catch (e: Exception) {
                errorKaryawan.postValue("Terjadi kesalahan saat membuat data")
                statusKaryawan.postValue(false)
                Log.d("KARYAWAN", "$e")
            }
        }
    }

    fun detailReservasiBengkel(id: Int){
        viewModelScope.launch {
            try {
                val detailReservasiResponse = repository.detailReservasiBengkel(id)
                detailReservasi.postValue(detailReservasiResponse.reservasi)
                status.postValue(true)
                Log.d("DETAIL RESERVASI", "$detailReservasiResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("DETAIL RESERVASI", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDetailReservasiBengkel::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("DETAIL RESERVASI", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("DETAIL RESERVASI", "$e")
            }
        }
    }
}