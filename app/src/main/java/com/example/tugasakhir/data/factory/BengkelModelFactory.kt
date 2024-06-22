package com.example.tugasakhir.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tugasakhir.data.repository.BengkelRepository
import com.example.tugasakhir.di.Injection
import com.example.tugasakhir.ui.screen.bengkel.BengkelViewModel
import com.example.tugasakhir.ui.screen.bengkeldetail.BengkelDetailViewModel

class BengkelModelFactory(private val repository: BengkelRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return when{
            modelClass.isAssignableFrom(BengkelViewModel::class.java) -> {
                BengkelViewModel(repository) as T
            }
            modelClass.isAssignableFrom(BengkelDetailViewModel::class.java) -> {
                BengkelDetailViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance: BengkelModelFactory? = null

        fun getInstance(context: Context): BengkelModelFactory =
            instance ?: synchronized(this){
                instance ?: BengkelModelFactory(Injection.provideBengkelRepository(context))
            }.also { instance = it }
    }
}