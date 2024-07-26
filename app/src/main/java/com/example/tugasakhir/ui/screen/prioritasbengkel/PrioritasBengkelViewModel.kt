package com.example.tugasakhir.ui.screen.prioritasbengkel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.PrioritasItem
import com.example.tugasakhir.api.response.ResponseDisplayPrioritas
import com.example.tugasakhir.api.response.ResponseJenisLayanan
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PrioritasBengkelViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val prioritasList = MutableLiveData<List<PrioritasItem?>?>()

    fun getPrioritas(idBengkel: Int){
        viewModelScope.launch {
            try {
                val prioritasResponse = repository.displayPrioritas(idBengkel)
                prioritasList.postValue(prioritasResponse.prioritas)
                status.postValue(true)
                Log.d("PRIORITAS", "$prioritasResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("PRIORITAS", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDisplayPrioritas::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("PRIORITAS", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("PRIORITAS", "$e")
            }
        }
    }
}