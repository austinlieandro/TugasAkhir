package com.example.tugasakhir.ui.screen.editkaryawan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ResponseDeleteKaryawan
import com.example.tugasakhir.api.response.ResponseUpdateKaryawan
import com.example.tugasakhir.data.repository.KaryawanRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class EditKaryawanViewModel(private val repository: KaryawanRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val errorDelete = MutableLiveData<String?>()
    val statusDelete: MutableLiveData<Boolean> = MutableLiveData()

    fun editKaryawan(bengkelId: Int, id: Int, nama_karyawan: String){
        viewModelScope.launch {
            try {
                val editKaryawanResponse = repository.updateKaryawan(bengkelId, id, nama_karyawan)
                status.postValue(true)
                Log.d("EDIT KARYAWAN", "$editKaryawanResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("EDIT KARYAWAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseUpdateKaryawan::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("EDIT KARYAWAN", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("EDIT KARYAWAN", "$e")
            }
        }
    }

    fun deleteKaryawan(bengkelId: Int, id: Int){
        viewModelScope.launch {
            try {
                val deleteKaryawanResponse = repository.deleteKaryawan(bengkelId, id)
                statusDelete.postValue(true)
                Log.d("DELETE KARYAWAN", "$deleteKaryawanResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("DELETE KARYAWAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDeleteKaryawan::class.java)
                errorDelete.postValue(errorBody.message)
                statusDelete.postValue(false)
                Log.d("DELETE KARYAWAN", "$e")
            }catch (e: Exception) {
                errorDelete.postValue("Terjadi kesalahan saat membuat data")
                statusDelete.postValue(false)
                Log.d("DELETE KARYAWAN", "$e")
            }
        }
    }
}