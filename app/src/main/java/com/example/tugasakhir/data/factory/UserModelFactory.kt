package com.example.tugasakhir.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tugasakhir.data.repository.UserRepository
import com.example.tugasakhir.di.Injection
import com.example.tugasakhir.ui.screen.login.LoginViewModel
import com.example.tugasakhir.ui.screen.profile.ProfileViewModel
import com.example.tugasakhir.ui.screen.register.RegisterViewModel
import com.example.tugasakhir.ui.screen.welcome.WelcomeViewModel

class UserModelFactory(private val repository: UserRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return when{
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance: UserModelFactory? = null

        fun getInstance(context: Context): UserModelFactory =
            instance ?: synchronized(this){
                instance ?: UserModelFactory(Injection.provideUserRepository(context))
            }.also { instance = it }
    }
}