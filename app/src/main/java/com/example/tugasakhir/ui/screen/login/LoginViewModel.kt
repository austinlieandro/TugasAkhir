package com.example.tugasakhir.ui.screen.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ResponseLogin
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun login(username: String, password: String){
        viewModelScope.launch {
            try {
                _loading.value = true
                val loginResponse = repository.login(username, password)
                status.postValue(true)
                val idUser = loginResponse.user?.id
                val userBengkel = loginResponse.user?.userBengkel
                val idBengkelUser = loginResponse.bengkelsId
                runBlocking { repository.saveSession(UserModel(username, true, idUser ?: 0, userBengkel ?: "", idBengkelUser ?: 0) ) }
                Log.d("LOGIN", "$loginResponse")
            }catch (e: HttpException){
                _loading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
//                val errorBody = Gson().fromJson(jsonInString, ResponseLogin::class.java)
//                val errorMessage = errorBody?.message ?: "Terjadi kesalahan saat login"
//                error.postValue(errorMessage)
                status.postValue(false)
                Log.d("LOGIN", "$e")
            } catch (e: Exception) {
                _loading.value = false
                error.postValue("Terjadi kesalahan saat login")
                status.postValue(false)
                Log.d("LOGIN", "$e")
            }
        }
    }
}