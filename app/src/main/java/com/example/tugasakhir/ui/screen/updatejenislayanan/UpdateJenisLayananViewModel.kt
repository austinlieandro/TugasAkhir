package com.example.tugasakhir.ui.screen.updatejenislayanan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.DetailJenisLayanan
import com.example.tugasakhir.api.response.JenisLayananItem
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseDetailJenisLayanan
import com.example.tugasakhir.api.response.ResponseUpdateBengkel
import com.example.tugasakhir.api.response.ResponseUpdateJenisLayanan
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UpdateJenisLayananViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()
    val errorUpdate = MutableLiveData<String?>()
    val statusUpdate: MutableLiveData<Boolean> = MutableLiveData()

    val jenisLayananBengkel = MutableLiveData<DetailJenisLayanan?>()

    fun getDetailJenisLayanan(id: Int){
        viewModelScope.launch {
            try {
                val detailJenisLayananResponse = repository.detailJenisLayanan(id)
                jenisLayananBengkel.postValue(detailJenisLayananResponse.jenisLayanan)
                status.postValue(true)
                Log.d("DETAIL JENIS LAYANAN", "$detailJenisLayananResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("DETAIL JENIS LAYANAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDetailJenisLayanan::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("DETAIL JENIS LAYANAN", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("DETAIL JENIS LAYANAN", "$e")
            }
        }
    }

    fun updateJenisLayanan(
        bengkels_id: Int,
        id: Int,
        nama_layanan: String,
        jenis_layanan: List<String>,
        harga_layanan: Int
    ){
        viewModelScope.launch {
            try {
                val updateJenisLayananResponse = repository.updateJenisLayanan(bengkels_id, id, nama_layanan, jenis_layanan, harga_layanan)
                statusUpdate.postValue(true)
                Log.d("UPDATE JENIS LAYANAN", "$updateJenisLayananResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("UPDATE JENIS LAYANAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseUpdateJenisLayanan::class.java)
                errorUpdate.postValue(errorBody.message)
                statusUpdate.postValue(false)
                Log.d("UPDATE JENIS LAYANAN", "$e")
            }catch (e: Exception) {
                errorUpdate.postValue("Terjadi kesalahan saat membuat data")
                statusUpdate.postValue(false)
                Log.d("UPDATE JENIS LAYANAN", "$e")
            }
        }
    }
}