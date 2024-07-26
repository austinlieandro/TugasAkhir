package com.example.tugasakhir.ui.screen.prioritashargabengkel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.PrioritasHarga
import com.example.tugasakhir.api.response.PrioritasItem
import com.example.tugasakhir.api.response.ResponseDisplayPrioritas
import com.example.tugasakhir.api.response.ResponseDisplayPrioritasHarga
import com.example.tugasakhir.data.repository.BengkelRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PrioritasHargaBengkelViewModel(private val repository: BengkelRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val prioritasList = MutableLiveData<List<PrioritasHarga?>?>()

    fun getPrioritasHarga(idBengkel: Int){
        viewModelScope.launch {
            try {
                val prioritasResponse = repository.displayPrioritasHarga(idBengkel)
                prioritasList.postValue(prioritasResponse.prioritas)
                status.postValue(true)
                Log.d("PRIORITAS HARGA", "$prioritasResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("PRIORITAS HARGA", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseDisplayPrioritasHarga::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("PRIORITAS HARGA", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("PRIORITAS HARGA", "$e")
            }
        }
    }
}