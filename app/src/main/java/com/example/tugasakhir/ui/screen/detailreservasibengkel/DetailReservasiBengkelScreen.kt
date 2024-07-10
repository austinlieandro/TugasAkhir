package com.example.tugasakhir.ui.screen.detailreservasibengkel

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.BengkelModelFactory
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>
@Composable
fun DetailReservasiBengkelScreen(
    navigator: DestinationsNavigator,
    bengkelId: Int,
    reservasiId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailReservasiBengkelViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    )
){
    val statusState by viewModel.status.observeAsState(false)
    val detailReservasiState = viewModel.detailReservasi.observeAsState()
    val listKaryawanState = viewModel.karyawanList.observeAsState()
    var isExpendedKaryawan by remember { mutableStateOf(false) }
    var selectedTextKaryawan by remember { mutableStateOf("") }
    var isExpendedStatus by remember { mutableStateOf(false) }
    val listStatus = remember { mutableListOf("Menunggu", "Proses", "Selesai", "Dibatalkan", "Relokasi") }
    var selectedTextStatus by remember { mutableStateOf("") }
    var idSelectedKarywan by remember { mutableStateOf(0) }

    val context = LocalContext.current

    LaunchedEffect(reservasiId) {
        viewModel.detailReservasiBengkel(reservasiId)
        viewModel.getKaryawan(bengkelId)
    }

    LaunchedEffect(detailReservasiState.value) {
        detailReservasiState.value?.let { detail ->
            selectedTextKaryawan = detail.namaKaryawan ?: ""
            selectedTextStatus = detail.statusReservasi ?: ""
        }
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
            Column(
                modifier = modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Detail Reservasi",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = "Nama pelanggan: ${detailReservasiState.value?.userName}",
                    modifier = modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Nomor pelanggan: ${detailReservasiState.value?.userPhone}",
                    modifier = modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Kendaraan pelanggan: ${detailReservasiState.value?.kendaraanReservasi}",
                    modifier = modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Merek kendaraan pelanggan: ${detailReservasiState.value?.merekKendaraan}",
                    modifier = modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Plat kendaraan pelanggan: ${detailReservasiState.value?.platKendaraan}",
                    modifier = modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Kendala Pelanggan: ${detailReservasiState.value?.namaLayanan}",
                    modifier = modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Rincian Kendala: ${detailReservasiState.value?.detailReservasi}",
                    modifier = modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Jam Reservasi: ${detailReservasiState.value?.jamReservasi}",
                    modifier = modifier
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Tanggal Reservasi: ${detailReservasiState.value?.tanggalReservasi}",
                    modifier = modifier
                        .padding(bottom = 8.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = isExpendedKaryawan,
                    onExpandedChange = { isExpendedKaryawan = !isExpendedKaryawan }
                ) {
                    TextField(
                        value = selectedTextKaryawan,
                        onValueChange = {},
                        readOnly = true,
                        shape = RoundedCornerShape(10.dp),
                        label = { Text("Pilih Karyawan") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.outline,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            containerColor = Color.White,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpendedKaryawan)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = isExpendedKaryawan,
                        onDismissRequest = { isExpendedKaryawan = false },
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        listKaryawanState.value?.forEach { option ->
                            DropdownMenuItem(
                                text = { option?.namaKaryawan?.let { Text(it) } },
                                onClick = {
                                    option?.id?.let { idSelectedKarywan = it }
                                    option?.namaKaryawan?.let { selectedTextKaryawan = it }
                                    isExpendedKaryawan = false
                                },
                                modifier = Modifier
                                    .background(Color.White)
                            )
                        }
                    }
                }
                ExposedDropdownMenuBox(
                    expanded = isExpendedStatus,
                    onExpandedChange = { isExpendedStatus = !isExpendedStatus }
                ) {
                    TextField(
                        value = selectedTextStatus,
                        onValueChange = {},
                        readOnly = true,
                        shape = RoundedCornerShape(10.dp),
                        label = { Text("Pilih Status") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.outline,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            containerColor = Color.White,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpendedStatus)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = isExpendedStatus,
                        onDismissRequest = { isExpendedStatus = false },
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        listStatus.forEach { option ->
                            DropdownMenuItem(
                                text = { option.let { Text(it) } },
                                onClick = {
                                    option.let { selectedTextStatus = it }
                                    isExpendedStatus = false
                                },
                                modifier = Modifier
                                    .background(Color.White)
                            )
                        }
                    }
                }
                Button(
                    onClick = {
                        viewModel.assignReservasi(
                            detailReservasiState.value?.id ?: 0,
                            idSelectedKarywan,
                            selectedTextStatus.lowercase()
                        )
                        Toast.makeText(context, "Berhasil Assign Karyawan", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = modifier
                        .padding(top = 16.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = "Update Reservasi"
                    )
                }
            }
        }
    }
}
