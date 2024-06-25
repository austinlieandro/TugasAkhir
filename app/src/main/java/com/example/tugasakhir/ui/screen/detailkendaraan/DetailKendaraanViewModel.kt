package com.example.tugasakhir.ui.screen.detailkendaraan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.DetailKendaraan
import com.example.tugasakhir.api.response.ResponseDeleteKendaraan
import com.example.tugasakhir.api.response.ResponseDetailKendaraan
import com.example.tugasakhir.api.response.ResponseDetailReservasiBengkel
import com.example.tugasakhir.api.response.ResponseUpdateKendaraan
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailKendaraanViewModel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val errorUpdate = MutableLiveData<String?>()
    val statusUpdate: MutableLiveData<Boolean> = MutableLiveData()

    val errorDelete = MutableLiveData<String?>()
    val statusDelete: MutableLiveData<Boolean> = MutableLiveData()

    val detailKendaraan = MutableLiveData<DetailKendaraan?>()

    fun detailKendaraanUser(usersId: Int, kendaraan_id: Int){
        viewModelScope.launch {
            try {
                val detailKendaraanResponse = repository.detailKendaraan(usersId, kendaraan_id)
                detailKendaraan.postValue(detailKendaraanResponse.kendaraan)
                status.postValue(true)
                Log.d("DETAIL KENDARAAN", "$detailKendaraanResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("DETAIL KENDARAAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDetailKendaraan::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("DETAIL KENDARAAN", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("DETAIL KENDARAAN", "$e")
            }
        }
    }

    fun updateKendaraanUser(usersId: Int, kendaraanId: Int, plat_kendaraan: String, merek_kendaraan: String){
        viewModelScope.launch {
            try {
                val updateKendaraanResponse = repository.updateKendaraan(usersId, kendaraanId, plat_kendaraan, merek_kendaraan)
                statusUpdate.postValue(true)
                Log.d("UPDATE KENDARAAN", "$updateKendaraanResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("UPDATE KENDARAAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseUpdateKendaraan::class.java)
                errorUpdate.postValue(errorBody.message)
                statusUpdate.postValue(false)
                Log.d("UPDATE KENDARAAN", "$e")
            }catch (e: Exception) {
                errorUpdate.postValue("Terjadi kesalahan saat membuat data")
                statusUpdate.postValue(false)
                Log.d("UPDATE KENDARAAN", "$e")
            }
        }
    }

    fun deleteKendaraanUser(usersId: Int, kendaraan_id: Int){
        viewModelScope.launch {
            try {
                val deleteKendaraanResponse = repository.deleteKendaraan(usersId, kendaraan_id)
                statusDelete.postValue(true)
                Log.d("DELETE KENDARAAN", "$deleteKendaraanResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("DELETE KENDARAAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDeleteKendaraan::class.java)
                errorDelete.postValue(errorBody.message)
                statusDelete.postValue(false)
                Log.d("DELETE KENDARAAN", "$e")
            }catch (e: Exception) {
                errorDelete.postValue("Terjadi kesalahan saat membuat data")
                statusDelete.postValue(false)
                Log.d("DELETE KENDARAAN", "$e")
            }
        }
    }
}