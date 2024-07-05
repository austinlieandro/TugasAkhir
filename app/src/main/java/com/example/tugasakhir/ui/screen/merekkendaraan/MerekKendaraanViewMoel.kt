package com.example.tugasakhir.ui.screen.merekkendaraan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.BengkelItem
import com.example.tugasakhir.api.response.MerekKendaraanItem
import com.example.tugasakhir.api.response.ResponseDisplayBengkel
import com.example.tugasakhir.api.response.ResponseDisplayMerekKendaraan
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MerekKendaraanViewMoel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val merekKendaraanList = MutableLiveData<List<MerekKendaraanItem?>?>()

    fun getMerekKendaraan(){
        viewModelScope.launch {
            try {
                val responseMerekKendaraan = repository.displayMerekKendaraan()
                merekKendaraanList.postValue(responseMerekKendaraan.merekKendaraan)
                status.postValue(true)
                Log.d("MEREK KENDARAAN", "$responseMerekKendaraan")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("MEREK KENDARAAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDisplayMerekKendaraan::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("MEREK KENDARAAN", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("MEREK KENDARAAN", "$e")
            }
        }
    }
}