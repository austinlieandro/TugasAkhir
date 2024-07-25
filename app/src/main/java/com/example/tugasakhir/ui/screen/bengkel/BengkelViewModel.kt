package com.example.tugasakhir.ui.screen.bengkel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.BengkelItem
import com.example.tugasakhir.api.response.ResponseDisplayBengkel
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class BengkelViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val bengkelList = MutableLiveData<List<BengkelItem?>?>()

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _jenisKendaraan = mutableStateOf("")
    val jenisKendaraan: State<String> get() = _jenisKendaraan

    init {
        getBengkel()
    }

    fun getBengkel(){
        viewModelScope.launch {
            try {
                val bengkelResponse = repository.getBengkel()
                bengkelList.postValue(bengkelResponse.bengkel)
                status.postValue(true)
                Log.d("BENGKEL", "${bengkelList.value}")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("BENGKEL", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDisplayBengkel::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("BENGKEL", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("BENGKEL", "$e")
            }
        }
    }

    fun search(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            val shopSearch = repository.searchBengkel(_query.value)
            bengkelList.postValue(shopSearch)
        }
    }

    fun setJenisKendaraan(jenisKendaraan: String) {
        viewModelScope.launch {
            _jenisKendaraan.value = jenisKendaraan
            val shopSearch = repository.jenisKendaraan(jenisKendaraan)
            bengkelList.postValue(shopSearch)
        }
    }
}