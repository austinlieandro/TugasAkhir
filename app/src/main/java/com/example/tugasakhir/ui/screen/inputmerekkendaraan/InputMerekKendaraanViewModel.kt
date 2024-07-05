package com.example.tugasakhir.ui.screen.inputmerekkendaraan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.MerekKendaraanItem
import com.example.tugasakhir.api.response.ResponseDisplayMerekKendaraan
import com.example.tugasakhir.api.response.ResponseInputKendaraan
import com.example.tugasakhir.api.response.ResponseInputMerekKendaraan
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class InputMerekKendaraanViewModel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    fun inputMerekKendaraan(jenis_kendaraan: String, merek_kendaraan: String, users_id: Int){
        viewModelScope.launch {
            try {
                val inputMerekKendaraan = repository.inputMerekKendaraan(jenis_kendaraan, merek_kendaraan, users_id)
                status.postValue(true)
                Log.d("INPUT MEREK KENDARAAN", "$inputMerekKendaraan")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("INPUT MEREK KENDARAAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseInputMerekKendaraan::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("INPUT MEREK KENDARAAN", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("INPUT MEREK KENDARAAN", "$e")
            }
        }
    }
}