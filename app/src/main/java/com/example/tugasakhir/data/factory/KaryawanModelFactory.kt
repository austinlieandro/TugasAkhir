package com.example.tugasakhir.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tugasakhir.data.repository.KaryawanRepository
import com.example.tugasakhir.di.Injection
import com.example.tugasakhir.ui.screen.editkaryawan.EditKaryawanViewModel
import com.example.tugasakhir.ui.screen.inputkaryawan.InputKaryawanViewModel
import com.example.tugasakhir.ui.screen.karyawan.KaryawanViewModel

class KaryawanModelFactory(private val repository: KaryawanRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return when{
            modelClass.isAssignableFrom(InputKaryawanViewModel::class.java) -> {
                InputKaryawanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(KaryawanViewModel::class.java) -> {
                KaryawanViewModel(repository) as T
            }
            modelClass.isAssignableFrom(EditKaryawanViewModel::class.java) -> {
                EditKaryawanViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance: KaryawanModelFactory? = null

        fun getInstance(context: Context): KaryawanModelFactory =
            instance ?: synchronized(this){
                instance ?: KaryawanModelFactory(Injection.provideKaryawanRepository(context))
            }.also { instance = it }
    }
}