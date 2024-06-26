package com.example.tugasakhir.ui.screen.updatejamoperasional

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ResponseAsignKaryawan
import com.example.tugasakhir.api.response.ResponseUpdateJamOperasional
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UpdateOperasionalViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    fun updateJamOperasional(bengkelsId: Int, jamId: Int, jam_operasional: String, slot: Int){
        viewModelScope.launch {
            try {
                val updateJamResponse = repository.updateJamOperasional(bengkelsId, jamId, jam_operasional, slot)
                status.postValue(true)
                Log.d("UPDATE JAM OPERASIONAL", "$updateJamResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("UPDATE JAM OPERASIONAL", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseUpdateJamOperasional::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("UPDATE JAM OPERASIONAL", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("UPDATE JAM OPERASIONAL", "$e")
            }
        }
    }
}