package com.example.tugasakhir.ui.screen.karyawan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.KaryawanItem
import com.example.tugasakhir.api.response.ResponseDisplayKaryawan
import com.example.tugasakhir.data.repository.KaryawanRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class KaryawanViewModel(private val repository: KaryawanRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val karyawanList = MutableLiveData<List<KaryawanItem?>?>()

    fun getKaryawan(id: Int){
        viewModelScope.launch {
            try {
                val karyawanResponse = repository.displayKaryawan(id)
                karyawanList.postValue(karyawanResponse.karyawan)
                status.postValue(true)
                Log.d("KARYAWAN", "$karyawanResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("KARYAWAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDisplayKaryawan::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("KARYAWAN", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("KARYAWAN", "$e")
            }
        }
    }
}