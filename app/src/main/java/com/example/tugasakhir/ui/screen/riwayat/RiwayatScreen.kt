package com.example.tugasakhir.ui.screen.riwayat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.RiwayatModelFactory
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.example.tugasakhir.di.Injection
import com.example.tugasakhir.ui.components.riwayat.RiwayatItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.UpdateeReservasiScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun RiwayatScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: RiwayatViewModel = viewModel(
        factory = RiwayatModelFactory(Injection.provideRiwayatRepository(LocalContext.current))
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore)
){
    val riwayatState = viewModel.riwayatReservasiList.observeAsState()
    val statusState by viewModel.status.observeAsState(false)
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))

    LaunchedEffect(userModel.id) {
        viewModel.getReservasi(userModel.id)
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (!statusState){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }else{
            LazyColumn(
                contentPadding = PaddingValues(bottom = 30.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
                    .padding(horizontal = 16.dp)
            ) {
                item{
                    Text(
                        text = "Riwayat Reservasi",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier
                            .padding(8.dp)
                    )
                }
                if(statusState){
                    items(riwayatState.value ?: emptyList()) {data ->
                        RiwayatItem(
                            namaBengkel = data?.namaBengkel ?: "",
                            lokasiBengkel = data?.lokasiBengkel ?: "",
                            alamatBengkel = data?.alamatBengkel ?: "",
                            numberBengkel = data?.numberBengkel ?: "",
                            statusBengkel = data?.statusReservasi ?: "",
                            tanggalReservasi = data?.tanggalReservasi ?: "",
                            jamReservasi = data?.jamReservasi ?: "",
                            gmapsBengkel = data?.gmapsBengkel ?: "",
                            merekKendaraan = data?.merekKendaraan ?: "",
                            platKendaraan = data?.platKendaraan ?: "",
                            jenisPerbaikan = data?.jenisLayanan?.joinToString(separator = ", ") ?: "",
                            modifier = modifier
                                .clickable {
                                    navigator.navigate(UpdateeReservasiScreenDestination(
                                        data?.id ?: 0,
                                        data?.tanggalReservasi ?: "",
                                        data?.jamReservasi ?: "",
                                        data?.kendaraanReservasi ?: "",
                                        data?.merekKendaraan ?: "",
                                        data?.nama_layanan ?: "",
                                        data?.detailReservasi ?: "",
                                        data?.bengkelsId ?: 0,
                                        data?.kendaraanId ?: 0,
                                        data?.jenisLayanan?.joinToString(separator = ", ") ?: "",
                                        data?.harga_layanan ?: 0,
                                        data?.usersId ?:0
                                    ))
                                }
                        )
                    }
                }
            }
        }
    }
}