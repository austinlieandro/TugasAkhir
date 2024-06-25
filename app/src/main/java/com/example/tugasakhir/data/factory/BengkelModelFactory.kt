package com.example.tugasakhir.data.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tugasakhir.data.repository.BengkelRepository
import com.example.tugasakhir.data.repository.KaryawanRepository
import com.example.tugasakhir.data.repository.UserRepository
import com.example.tugasakhir.di.Injection
import com.example.tugasakhir.ui.screen.bengkel.BengkelViewModel
import com.example.tugasakhir.ui.screen.bengkeldetail.BengkelDetailViewModel
import com.example.tugasakhir.ui.screen.daftarbengkel.DaftarBengkelViewModel
import com.example.tugasakhir.ui.screen.detailreservasibengkel.DetailReservasiBengkelViewModel
import com.example.tugasakhir.ui.screen.jamoperasional.JamOperasionalViewModel
import com.example.tugasakhir.ui.screen.reservasibengkel.ReservasiBengkelViewModel
import com.example.tugasakhir.ui.screen.updatebengkel.UpdateBengkelViewModel

class BengkelModelFactory(private val repository: BengkelRepository, private val repositoryUser: UserRepository, private val repositoryKaryawan: KaryawanRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        return when{
            modelClass.isAssignableFrom(BengkelViewModel::class.java) -> {
                BengkelViewModel(repository) as T
            }
            modelClass.isAssignableFrom(BengkelDetailViewModel::class.java) -> {
                BengkelDetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DaftarBengkelViewModel::class.java) -> {
                DaftarBengkelViewModel(repository, repositoryUser) as T
            }
            modelClass.isAssignableFrom(JamOperasionalViewModel::class.java) -> {
                JamOperasionalViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UpdateBengkelViewModel::class.java) -> {
                UpdateBengkelViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ReservasiBengkelViewModel::class.java) -> {
                ReservasiBengkelViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailReservasiBengkelViewModel::class.java) -> {
                DetailReservasiBengkelViewModel(repository, repositoryKaryawan) as T
            }
            else -> throw IllegalArgumentException("Unkown ViewModel class: " + modelClass.name)
        }
    }

    companion object{
        @Volatile
        private var instance: BengkelModelFactory? = null

        fun getInstance(context: Context): BengkelModelFactory =
            instance ?: synchronized(this){
                instance ?: BengkelModelFactory(Injection.provideBengkelRepository(context), Injection.provideUserRepository(context), Injection.provideKaryawanRepository(context))
            }.also { instance = it }
    }
}