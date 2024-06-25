package com.example.tugasakhir.ui.screen.reservasibengkel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.BengkelModelFactory
import com.example.tugasakhir.ui.components.reservasibengkel.ReservasiBengkelItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Destination<RootGraph>
@Composable
fun ReservasiBengkelScreen(
    bengkelId: Int,
    modifier: Modifier = Modifier,
    viewModel: ReservasiBengkelViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    )
){
    val reservasiListState = viewModel.reservasiList.observeAsState()
    val statusState by viewModel.status.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.getReservasiBengkel(bengkelId)
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .padding(horizontal = 16.dp)
        ){
            item {
                Text(
                    text = "List Reservasi",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .padding(8.dp)
                )
            }
            if (statusState){
                items(reservasiListState.value ?: emptyList()){data ->
                    ReservasiBengkelItem(
                        namaUser = data?.userName ?: "",
                        numberUser = data?.userPhone ?: "",
                        namaKaryawan = data?.namaKaryawan ?: "",
                        statusReservasi = data?.statusReservasi ?: "",
                        jenisKendalaReservasi = data?. jeniskendalaReservasi ?: "",
                        kendaraanReservasi = data?.kendaraanReservasi ?: "",
                        jamReservasi = data?.jamReservasi ?: "",
                        tanggalReservasi = data?.tanggalReservasi ?: "",
                        modifier = modifier
                            .clickable {

                            }
                    )
                }
            }
        }
    }
}