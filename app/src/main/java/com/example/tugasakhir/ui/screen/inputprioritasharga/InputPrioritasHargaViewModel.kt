package com.example.tugasakhir.ui.screen.inputprioritasharga

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseInputPriortiasHarga
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class InputPrioritasHargaViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    fun inputPrioritasHarga(harga: List<Int>, bobot_nilai: List<Int>, bengkels_id: Int){
        viewModelScope.launch {
            try {
                val inputPrioritasHargaResponse = repository.inputPrioritasHarga(harga, bobot_nilai, bengkels_id)
                status.postValue(true)
                Log.d("PRIORITAS HARGA", "$inputPrioritasHargaResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("PRIORITAS HARGA", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseInputPriortiasHarga::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("PRIORITAS HARGA", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("PRIORITAS HARGA", "$e")
            }
        }
    }
}