package com.example.tugasakhir.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tugasakhir.data.repository.RiwayatRepository
import com.example.tugasakhir.di.Injection
import com.example.tugasakhir.ui.screen.riwayat.RiwayatViewModel

class RiwayatModelFactory(private val repository: RiwayatRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return when{
            modelClass.isAssignableFrom(RiwayatViewModel::class.java) -> {
                RiwayatViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance: RiwayatModelFactory? = null

        fun getInstance(context: Context): RiwayatModelFactory =
            instance ?: synchronized(this){
                instance ?: RiwayatModelFactory(Injection.provideRiwayatRepository(context))
            }.also { instance = it }
    }
}