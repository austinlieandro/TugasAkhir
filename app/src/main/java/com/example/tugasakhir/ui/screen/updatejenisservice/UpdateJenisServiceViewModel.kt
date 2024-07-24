package com.example.tugasakhir.ui.screen.updatejenisservice

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ResponseUpdateJenisService
import com.example.tugasakhir.api.response.ResponseUpdateMerekKendaraan
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UpdateJenisServiceViewModel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    fun updateJenisService(usersId: Int, serviceId: Int, nama_service: String){
        viewModelScope.launch {
            try {
                val updateJenisService = repository.updateJenisService(usersId, serviceId, nama_service)
                status.postValue(true)
                Log.d("UPDATE JENIS SERVICE", "$updateJenisService")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("UPDATE JENIS SERVICE", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseUpdateJenisService::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("UPDATE JENIS SERVICE", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("UPDATE JENIS SERVICE", "$e")
            }
        }
    }
}