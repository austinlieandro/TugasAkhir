package com.example.tugasakhir.ui.screen.jamoperasional

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.Bengkel
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseJamOperasionalBengkel
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class JamOperasionalViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val errorJamOperasional = MutableLiveData<String?>()
    val statusJamOperasional: MutableLiveData<Boolean> = MutableLiveData()

    val detailBengkel = MutableLiveData<Bengkel?>()

    fun detailBengkel(idUser: Int, id: Int, tanggal_reservasi: String, jam_reservasi: String){
        viewModelScope.launch {
            try {
                val detailResponse = repository.getDetailBengkel(idUser, id, tanggal_reservasi, jam_reservasi)
                detailBengkel.postValue(detailResponse.bengkel)
                status.postValue(true)
                Log.d("JAM OPERASIONAL", "$detailResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("JAM OPERASIONAL", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDetailBengkel::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("JAM OPERASIONAL", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("JAM OPERASIONAL", "$e")
            }
        }
    }

    fun daftarJamOperasional(jam_operasional: List<String>, hari_operasional: List<String>, slot: List<Int>, bengkels_id: Int){
        viewModelScope.launch {
            try {
                val daftarJamOperasionalResponse = repository.daftarJamOperasional(jam_operasional, hari_operasional, slot, bengkels_id)
                statusJamOperasional.postValue(true)
                Log.d("DAFTAR JAM OPERASIONAL", " $daftarJamOperasionalResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("DAFTAR JAM OPERASIONAL", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseJamOperasionalBengkel::class.java)
                errorJamOperasional.postValue(errorBody.message)
                statusJamOperasional.postValue(false)
                Log.d("DAFTAR JAM OPERASIONAL", "$e")
            }catch (e: Exception) {
                errorJamOperasional.postValue("Terjadi kesalahan saat membuat data")
                statusJamOperasional.postValue(false)
                Log.d("DAFTAR JAM OPERASIONAL", "$e")
            }
        }
    }
}