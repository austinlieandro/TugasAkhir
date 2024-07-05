package com.example.tugasakhir.ui.screen.kendaraan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.KendaraanItem
import com.example.tugasakhir.api.response.ResponseDisplayKendaraan
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class KendaraanViewModel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val kendaraanList = MutableLiveData<List<KendaraanItem?>?>()

    fun getKendaraan(id: Int){
        viewModelScope.launch {
            try {
                val kendaraanResponse = repository.displayKendaraan(id)
                kendaraanList.postValue(kendaraanResponse.kendaraan)
                status.postValue(true)
                Log.d("KENDARAAN", "$kendaraanResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("KENDARAAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDisplayKendaraan::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("KENDARAAN", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("KENDARAAN", "$e")
            }
        }
    }
}