package com.example.tugasakhir.ui.screen.inputkaryawan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ResponseInputKaryawan
import com.example.tugasakhir.data.repository.KaryawanRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class InputKaryawanViewModel(private val repository: KaryawanRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    fun inputKaryawan(nama_karyawan: String, bengkels_id: Int){
        viewModelScope.launch {
            try {
                val inputKaryawanResponse = repository.inputKaryawanBengkel(nama_karyawan, bengkels_id)
                status.postValue(true)
                Log.d("INPUT KARYAWAN", "$inputKaryawanResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("INPUT KARYAWAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseInputKaryawan::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("INPUT KARYAWAN", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("INPUT KARYAWAN", "$e")
            }
        }
    }
}