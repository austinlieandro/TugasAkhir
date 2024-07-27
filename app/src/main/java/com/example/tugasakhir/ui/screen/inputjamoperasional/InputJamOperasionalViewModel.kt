package com.example.tugasakhir.ui.screen.inputjamoperasional

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.Bengkel
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseInputJamOperasionalSatuan
import com.example.tugasakhir.api.response.ResponseInputJenisLayanan
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class InputJamOperasionalViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    fun inputJamOperasional(
        jam_operasional: String,
        hari_operasional: String,
        slot: Int,
        bengkels_id: Int
    ){
        viewModelScope.launch {
            try {
                val inputJamOperasionalResponse = repository.inputJamOperasionalSatuan(
                    jam_operasional,
                    hari_operasional,
                    slot,
                    bengkels_id
                )
                status.postValue(true)
                Log.d("JAM OPERASIONAL", "$inputJamOperasionalResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("JAM OPERASIONAL", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseInputJamOperasionalSatuan::class.java)
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

    val detailBengkel = MutableLiveData<Bengkel?>()

    fun detailBengkel(idUser: Int, id: Int){
        viewModelScope.launch {
            try {
                val detailResponse = repository.getDetailBengkel(idUser, id)
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
}