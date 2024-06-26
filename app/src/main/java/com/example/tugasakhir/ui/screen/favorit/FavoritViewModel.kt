package com.example.tugasakhir.ui.screen.favorit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.FavoritesItem
import com.example.tugasakhir.api.response.ResponseFavoritBengkel
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class FavoritViewModel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val favoritList = MutableLiveData<List<FavoritesItem?>?>()

    fun getFavorit(id: Int){
        viewModelScope.launch {
            try {
                val favoritResponse = repository.favoriteBengkel(id)
                favoritList.postValue(favoritResponse.favorites)
                status.postValue(true)
                Log.d("FAVORIT", "$favoritResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("FAVORIT", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseFavoritBengkel::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("FAVORIT", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("FAVORIT", "$e")
            }
        }
    }
}