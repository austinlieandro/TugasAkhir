package com.example.tugasakhir.ui.screen.updateprofile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.api.response.ResponseProfile
import com.example.tugasakhir.api.response.ResponseUpdateProfile
import com.example.tugasakhir.api.response.UsersProfile
import com.example.tugasakhir.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UpdateProfileViewModel(private val repository: UserRepository): ViewModel() {
    val error = MutableLiveData<String?>()
    val status: MutableLiveData<Boolean> = MutableLiveData()

    val errorUpdate = MutableLiveData<String?>()
    val statusUpdate: MutableLiveData<Boolean> = MutableLiveData()

    val profile = MutableLiveData<UsersProfile?>()

    fun getProfile(id: Int){
        viewModelScope.launch {
            try {
                val profileResponse = repository.profile(id)
                profile.postValue(profileResponse.users)
                status.postValue(true)
                Log.d("PROFILE", "$profileResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("PROFILE", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseProfile::class.java)
                error.postValue(errorBody.message)
                status.postValue(false)
                Log.d("PROFILE", "$e")
            }catch (e: Exception) {
                error.postValue("Terjadi kesalahan saat membuat data")
                status.postValue(false)
                Log.d("PROFILE", "$e")
            }
        }
    }

    fun updateProfile(id: Int, name: String, email: String, username: String, password: String, phone: String){
        viewModelScope.launch {
            try {
                val updateProfileResponse = repository.updateProfile(id, name, email, username, password, phone)
                statusUpdate.postValue(true)
                errorUpdate.postValue(updateProfileResponse.message)
                Log.d("UPDATE PROFILE", "$updateProfileResponse")
            }catch (e: HttpException){
                val jsonInString = e.response()?.errorBody()?.string()
                Log.e("UPDATE PROFILE", "Error response: $jsonInString")
                val errorBody = Gson().fromJson(jsonInString, ResponseUpdateProfile::class.java)
                errorUpdate.postValue(errorBody.message)
                statusUpdate.postValue(false)
                Log.d("UPDATE PROFILE", "$e")
            }catch (e: Exception) {
                errorUpdate.postValue("Terjadi kesalahan saat membuat data")
                statusUpdate.postValue(false)
                Log.d("UPDATE PROFILE", "$e")
            }
        }
    }
}