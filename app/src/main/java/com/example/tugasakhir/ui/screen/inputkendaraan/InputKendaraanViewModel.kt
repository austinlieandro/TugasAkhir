package com.example.tugasakhir.ui.screen.inputkendaraan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ResponseInputKendaraan
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class InputKendaraanViewModel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    fun inputKendaraan(jenis_kendaraan: String, plat_kendaraan: String, merek_kendaraan: String, users_id: Int){
        viewModelScope.launch {
            try {
                val inputKendaraanResponse = repository.inputKendaraan(jenis_kendaraan, plat_kendaraan, merek_kendaraan, users_id)
                status.postValue(true)
                Log.d("INPUT KENDARAAN", "$inputKendaraanResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("INPUT KENDARAAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseInputKendaraan::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("INPUT KENDARAAN", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("INPUT KENDARAAN", "$e")
            }
        }
    }
}