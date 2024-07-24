package com.example.tugasakhir.ui.screen.inputjenisservice

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ResponseInputJenisService
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class InputJenisServiceViewModel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    fun inputJenisService(nama_service: String, users_id: Int){
        viewModelScope.launch {
            try {
                val inputJenisServiceResponse = repository.inputJenisService(nama_service, users_id)
                status.postValue(true)
                Log.d("INPUT JENIS SERVICE", "$inputJenisServiceResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("INPUT JENIS SERVICE", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseInputJenisService::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("INPUT JENIS SERVICE", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("INPUT JENIS SERVICE", "$e")
            }
        }
    }
}