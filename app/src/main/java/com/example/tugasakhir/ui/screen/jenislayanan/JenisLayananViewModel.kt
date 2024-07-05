package com.example.tugasakhir.ui.screen.jenislayanan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.DisplayJenisLayanan
import com.example.tugasakhir.api.response.ResponseDisplayBengkel
import com.example.tugasakhir.api.response.ResponseJenisLayanan
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class JenisLayananViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val jenisLayananList = MutableLiveData<List<DisplayJenisLayanan?>?>()

    fun getJenisLayanan(bengkels_id: Int){
        viewModelScope.launch {
            try {
                val jenisLayananResponse = repository.getJenisLayanan(bengkels_id)
                jenisLayananList.postValue(jenisLayananResponse.jenisLayanan)
                status.postValue(true)
                Log.d("JENIS LAYANAN", "$jenisLayananResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("JENIS LAYANAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseJenisLayanan::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("JENIS LAYANAN", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("JENIS LAYANAN", "$e")
            }
        }
    }
}