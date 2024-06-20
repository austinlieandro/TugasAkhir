package com.example.tugasakhir.ui.screen.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.repository.UserRepository

class WelcomeViewModel(private val repository: UserRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}