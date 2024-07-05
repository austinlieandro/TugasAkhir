package com.example.tugasakhir.ui.screen.inputkendaraan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.MerekKendaraanItem
import com.example.tugasakhir.api.response.ResponseDisplayMerekKendaraan
import com.example.tugasakhir.api.response.ResponseInputKendaraan
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class InputKendaraanViewModel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()
    val errorMerek = MutableLiveData<String?>()
    val statusMerek: MutableLiveData<Boolean> = MutableLiveData()

    val merekKendaraanList = MutableLiveData<List<MerekKendaraanItem?>?>()

    fun inputKendaraan(jenis_kendaraan: String, plat_kendaraan: String, users_id: Int, merek_kendaraan_id: Int){
        viewModelScope.launch {
            try {
                val inputKendaraanResponse = repository.inputKendaraan(jenis_kendaraan, plat_kendaraan, users_id, merek_kendaraan_id )
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

    fun getMerekKendaraan(){
        viewModelScope.launch {
            try {
                val responseMerekKendaraan = repository.displayMerekKendaraan()
                merekKendaraanList.postValue(responseMerekKendaraan.merekKendaraan)
                statusMerek.postValue(true)
                Log.d("MEREK KENDARAAN", "$responseMerekKendaraan")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("MEREK KENDARAAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDisplayMerekKendaraan::class.java)
                errorMerek.postValue(errorBody.message)
                statusMerek.postValue(false)
                Log.d("MEREK KENDARAAN", "$e")
            }catch (e: Exception) {
                errorMerek.postValue("Terjadi kesalahan saat membuat data")
                statusMerek.postValue(false)
                Log.d("MEREK KENDARAAN", "$e")
            }
        }
    }
}