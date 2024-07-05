package com.example.tugasakhir.ui.screen.updatemerekkendaraan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ResponseInputMerekKendaraan
import com.example.tugasakhir.api.response.ResponseUpdateMerekKendaraan
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UpdateMerekKendaraanViewModel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    fun updateMerekKendaraan(usersId: Int, merekKendaraanId: Int, merek_kendaraan: String){
        viewModelScope.launch {
            try {
                val updateMerekKendaraanResponse = repository.updateMerekKendaraan(usersId, merekKendaraanId, merek_kendaraan)
                status.postValue(true)
                Log.d("UPDATE MEREK KENDARAAN", "$updateMerekKendaraanResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("UPDATE MEREK KENDARAAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseUpdateMerekKendaraan::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("UPDATE MEREK KENDARAAN", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("UPDATE MEREK KENDARAAN", "$e")
            }
        }
    }
}