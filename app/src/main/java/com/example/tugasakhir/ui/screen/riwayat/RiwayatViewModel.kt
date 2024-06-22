package com.example.tugasakhir.ui.screen.riwayat

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ReservasiItem
import com.example.tugasakhir.api.response.ResponseRiwayatReservasi
import com.example.tugasakhir.data.repository.RiwayatRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RiwayatViewModel(private val repository: RiwayatRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val riwayatReservasiList = MutableLiveData<List<ReservasiItem?>?>()

    fun getReservasi(id: Int){
        viewModelScope.launch {
            try{
                val reservasiResponse = repository.getRiwayatReservasi(id)
                riwayatReservasiList.postValue(reservasiResponse.reservasi)
                status.postValue(true)
                Log.d("RIWAYAT", "$reservasiResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("RIWAYAT", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseRiwayatReservasi::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("RIWAYAT", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("RIWAYAT", "$e")
            }
        }
    }
}