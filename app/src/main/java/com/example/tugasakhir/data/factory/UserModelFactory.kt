package com.example.tugasakhir.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tugasakhir.data.repository.UserRepository
import com.example.tugasakhir.di.Injection
import com.example.tugasakhir.ui.screen.detailkendaraan.DetailKendaraanViewModel
import com.example.tugasakhir.ui.screen.favorit.FavoritViewModel
import com.example.tugasakhir.ui.screen.inputjenisservice.InputJenisServiceViewModel
import com.example.tugasakhir.ui.screen.inputkendaraan.InputKendaraanViewModel
import com.example.tugasakhir.ui.screen.inputmerekkendaraan.InputMerekKendaraanViewModel
import com.example.tugasakhir.ui.screen.jenisservice.JenisServiceViewModel
import com.example.tugasakhir.ui.screen.kendaraan.KendaraanViewModel
import com.example.tugasakhir.ui.screen.login.LoginViewModel
import com.example.tugasakhir.ui.screen.merekkendaraan.MerekKendaraanViewMoel
import com.example.tugasakhir.ui.screen.profile.ProfileViewModel
import com.example.tugasakhir.ui.screen.register.RegisterViewModel
import com.example.tugasakhir.ui.screen.updatejenisservice.UpdateJenisServiceViewModel
import com.example.tugasakhir.ui.screen.updatemerekkendaraan.UpdateMerekKendaraanViewModel
import com.example.tugasakhir.ui.screen.updateprofile.UpdateProfileViewModel
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
            modelClass.isAssignableFrom(KendaraanViewModel::class.java) -> {
                KendaraanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(InputKendaraanViewModel::class.java) -> {
                InputKendaraanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailKendaraanViewModel::class.java) -> {
                DetailKendaraanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(FavoritViewModel::class.java) -> {
                FavoritViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UpdateProfileViewModel::class.java) -> {
                UpdateProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MerekKendaraanViewMoel::class.java) -> {
                MerekKendaraanViewMoel(repository) as T
            }
            modelClass.isAssignableFrom(InputMerekKendaraanViewModel::class.java) -> {
                InputMerekKendaraanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UpdateMerekKendaraanViewModel::class.java) -> {
                UpdateMerekKendaraanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(InputJenisServiceViewModel::class.java) -> {
                InputJenisServiceViewModel(repository) as T
            }
            modelClass.isAssignableFrom(JenisServiceViewModel::class.java) -> {
                JenisServiceViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UpdateJenisServiceViewModel::class.java) -> {
                UpdateJenisServiceViewModel(repository) as T
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