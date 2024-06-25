package com.example.tugasakhir.ui.screen.reservasibengkel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ReservasiBengkel
import com.example.tugasakhir.api.response.ResponseDisplayKaryawan
import com.example.tugasakhir.api.response.ResponseDisplayReservasiBengkel
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ReservasiBengkelViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val reservasiList = MutableLiveData<List<ReservasiBengkel?>?>()

    fun getReservasiBengkel(bengkelId: Int){
        viewModelScope.launch {
            try {
                val reservasiBengkelResponse = repository.displayReservasiBengkel(bengkelId)
                reservasiList.postValue(reservasiBengkelResponse.reservasi)
                status.postValue(true)
                Log.d("RESERVASI BENGKEL", "$reservasiBengkelResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("RESERVASI BENGKEL", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDisplayReservasiBengkel::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("RESERVASI BENGKEL", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("RESERVASI BENGKEL", "$e")
            }
        }
    }
}