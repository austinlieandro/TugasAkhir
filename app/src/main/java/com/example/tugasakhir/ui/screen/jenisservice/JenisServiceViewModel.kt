package com.example.tugasakhir.ui.screen.jenisservice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.JenisServiceItem
import com.example.tugasakhir.api.response.ResponseDisplayJenisService
import com.example.tugasakhir.api.response.ResponseDisplayMerekKendaraan
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class JenisServiceViewModel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    private val _jenisSeviceList = MutableLiveData<List<JenisServiceItem?>?>()
    val jenisSeviceList: LiveData<List<JenisServiceItem?>?> = _jenisSeviceList

    fun getJenisService(){
        viewModelScope.launch {
            try {
                val responseDisplayJenisService = repository.displayJenisService()
                _jenisSeviceList.postValue(responseDisplayJenisService.jenisService)
                status.postValue(true)
                Log.d("JENIS SERVICE", "$responseDisplayJenisService")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("JENIS SERVICE", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDisplayJenisService::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("JENIS SERVICE", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("JENIS SERVICE", "$e")
            }
        }
    }

    fun isServiceChecked(serviceName: String): Boolean {
        val service = _jenisSeviceList.value?.find { it?.namaService == serviceName }
        return service?.isChecked ?: false
    }

    fun setServiceChecked(serviceName: String, isChecked: Boolean) {
        val updatedList = _jenisSeviceList.value?.map { serviceItem ->
            if (serviceItem?.namaService == serviceName) {
                serviceItem.isChecked = isChecked
            }
            serviceItem
        }
        _jenisSeviceList.postValue(updatedList)
        Log.d("SET SERVICE CHECKED", "Updated service $serviceName to $isChecked")
    }
}