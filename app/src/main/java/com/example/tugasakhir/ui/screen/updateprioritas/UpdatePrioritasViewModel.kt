package com.example.tugasakhir.ui.screen.updateprioritas

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ResponseUpdatePrioritas
import com.example.tugasakhir.api.response.ResponseUpdatePrioritasHarga
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UpdatePrioritasViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    fun updatePrioritas(bengkelsId: Int, id: Int, bobot_nilai: Int, bobot_estimasi: Int, bobot_urgensi: Int){
        viewModelScope.launch {
            try {
                val responseUpdate = repository.updatePrioritas(bengkelsId, id, bobot_nilai, bobot_estimasi, bobot_urgensi)
                status.postValue(true)
                Log.d("UPDATE PRIORITAS", "$responseUpdate")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("UPDATE PRIORITAS", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseUpdatePrioritas::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("UPDATE PRIORITAS", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("UPDATE PRIORITAS", "$e")
            }
        }
    }
}