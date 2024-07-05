package com.example.tugasakhir.ui.screen.inputjenislayanan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ResponseDaftarBengkel
import com.example.tugasakhir.api.response.ResponseInputJenisLayanan
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class InputJenisLayananViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    fun inputJenisLayanan(
        nama_layanan: String,
        jenis_layanan: List<String>,
        harga_layanan: Int,
        bengkels_id: Int,
    ){
        viewModelScope.launch {
            try {
                val inputJenisLayananResponse = repository.inputJenisLayanan(
                    nama_layanan,
                    jenis_layanan,
                    harga_layanan,
                    bengkels_id
                )
                status.postValue(true)
                Log.d("INPUT JENIS LAYANAN", "$inputJenisLayananResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("INPUT JENIS LAYANAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseInputJenisLayanan::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("INPUT JENIS LAYANAN", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("INPUT JENIS LAYANAN", "$e")
            }
        }
    }
}