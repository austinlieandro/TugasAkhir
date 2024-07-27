package com.example.tugasakhir.ui.screen.inputprioritassatuan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.Bengkel
import com.example.tugasakhir.api.response.JenisServiceItem
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.api.response.ResponseDisplayJenisService
import com.example.tugasakhir.api.response.ResponseInputPrioritasSatuan
import com.example.tugasakhir.data.repository.BengkelRepository
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class InputPrioritasSatuanViewModel(private val repository: BengkelRepository, private val userRepository: UserRepository):ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val errorInput = MutableLiveData<String?>()
    val statusInput: MutableLiveData<Boolean> = MutableLiveData()

    val detailBengkel = MutableLiveData<Bengkel?>()
    private val _jenisSeviceList = MutableLiveData<List<JenisServiceItem?>?>()
    val jenisSeviceList: LiveData<List<JenisServiceItem?>?> = _jenisSeviceList

    fun getJenisService(){
        viewModelScope.launch {
            try {
                val responseDisplayJenisService = userRepository.displayJenisService()
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

    fun getDetailBengkel(idUser: Int, id: Int){
        viewModelScope.launch {
            try {
                val detailBengkelResponse = repository.getDetailBengkel(idUser, id)
                detailBengkel.postValue(detailBengkelResponse.bengkel)
                status.postValue(true)
                Log.d("DETAIL BENGKEL", "Bengkel: ${detailBengkel.value}")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("DETAIL BENGKEL", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDetailBengkel::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("DETAIL BENGKEL", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("DETAIL BENGKEL", "$e")
            }
        }
    }

    fun inputPrioritasSatuan(jenis_kendaraan: String, jenis_kerusakan: String, bobot_nilai: Int, bobot_estimasi: Int, bobot_urgensi: Int, bengkels_id: Int){
        viewModelScope.launch {
            try {
                val responseInputPrioritasSatuan = repository.inputPrioritasSatuan(jenis_kendaraan, jenis_kerusakan, bobot_nilai, bobot_estimasi, bobot_urgensi, bengkels_id)
                statusInput.postValue(true)
                errorInput.postValue(responseInputPrioritasSatuan.message)
                Log.d("PRIORITAS SATUAN", "$responseInputPrioritasSatuan")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("PRIORITAS SATUAN", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseInputPrioritasSatuan::class.java)
                errorInput.postValue(errorBody.message)
                statusInput.postValue(false)
                Log.d("PRIORITAS SATUAN", "$e")
            }catch (e: Exception) {
                errorInput.postValue("Terjadi kesalahan saat membuat data")
                statusInput.postValue(false)
                Log.d("PRIORITAS SATUAN", "$e")
            }
        }
    }
}