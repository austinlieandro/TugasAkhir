package com.example.tugasakhir.ui.screen.updateprioritasharga

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ResponseUpdateMerekKendaraan
import com.example.tugasakhir.api.response.ResponseUpdatePrioritasHarga
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UpdatePrioritasHargaViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    fun updatePrioritasHarga(bengkelsId: Int, id: Int, harga: Int, bobot_nilai: Int){
        viewModelScope.launch {
            try {
                val updatePrioritasHargaResponse = repository.updatePrioritasHarga(bengkelsId, id, harga, bobot_nilai)
                status.postValue(true)
                Log.d("UPDATE PRIORITAS HARGA", "$updatePrioritasHargaResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("UPDATE PRIORITAS HARGA", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseUpdatePrioritasHarga::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("UPDATE PRIORITAS HARGA", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("UPDATE PRIORITAS HARGA", "$e")
            }
        }
    }
}