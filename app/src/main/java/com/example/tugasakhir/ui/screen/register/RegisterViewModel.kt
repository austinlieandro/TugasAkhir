package com.example.tugasakhir.ui.screen.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhir.api.response.ResponseRegister
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun register(name: String, phone: String, email: String, username: String, password: String){
        viewModelScope.launch{
            try {
                _loading.value = true
                val registerResponse = repository.register(name, phone, email, username, password)
                status.postValue(true)
                Log.d("REGISTER", "$registerResponse")
            } catch (e: retrofit2.HttpException) {
                _loading.value = false
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ResponseRegister::class.java)
                val errorMessage = errorBody?.message ?: "Terjadi kesalahan saat pendaftaran"
                error.postValue(errorMessage)
                status.postValue(false)
                Log.d("REGISTER", "$e")
            } catch (e: Exception) {
                _loading.value = false
                error.postValue("Terjadi kesalahan saat pendaftaran")
                status.postValue(false)
                Log.d("REGISTER", "$e")
            }
        }
    }
}