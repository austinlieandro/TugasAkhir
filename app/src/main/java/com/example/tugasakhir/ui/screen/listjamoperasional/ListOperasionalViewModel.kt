package com.example.tugasakhir.ui.screen.listjamoperasional

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.JamOperasionalItem
import com.example.tugasakhir.api.response.ResponseDetailBengkel
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ListOperasionalViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val listOperasional = MutableLiveData<List<JamOperasionalItem?>?>()

    fun getJamOperasional(idUser: Int, id: Int){
        viewModelScope.launch {
            try {
                val detailBengkelResponse = repository.getDetailBengkel(idUser, id)
                listOperasional.postValue(detailBengkelResponse.jamOperasional)
                status.postValue(true)
                Log.d("DETAIL JAM", "Bengkel: $detailBengkelResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("DETAIL JAM", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDetailBengkel::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("DETAIL JAM", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("DETAIL JAM", "$e")
            }
        }
    }
}