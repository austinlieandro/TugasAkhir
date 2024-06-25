package com.example.tugasakhir.ui.components.reservasibengkel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugasakhir.ui.theme.TugasAkhirTheme

@Composable
fun ReservasiBengkelItem(
    namaUser: String,
    numberUser: String,
    namaKaryawan: String,
    statusReservasi: String,
    jenisKendalaReservasi: String,
    kendaraanReservasi: String,
    jamReservasi: String,
    tanggalReservasi: String,
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(Color.White),
        modifier = modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(12.dp))
    ){
        Column {
            Text(
                text = "Nama Pelanggan: $namaUser",
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                fontSize = 22.sp,
                modifier = modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Text(
                text = "Nomor pelanggan: $numberUser",
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp)
            )
            Text(
                text = "Kendaraan Pelanggan: $kendaraanReservasi",
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp)
            )
            Text(
                text = "Kendala Pelanggan: $jenisKendalaReservasi",
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp, bottom = 8.dp)
            )
            Divider(
                color = Color.Black,
                modifier = modifier
                    .height(2.dp)
            )
            Text(
                text = if (namaKaryawan.isNullOrEmpty()){
                    "Silahkan Asign Karyawan"
                    }else{
                    "Nama Karyawan: $namaKaryawan"
                }
                ,
                maxLines = 1,
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp, top = 8.dp)
            )
            Text(
                text = "Tanggal Reservasi: $tanggalReservasi",
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp)
            )
            Text(
                text = "Jam Reservasi: $jamReservasi",
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp, bottom = 8.dp)
            )
            Box(
                modifier = modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd,
            ) {
                Box(
                    modifier = modifier
                        .padding(start = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (statusReservasi.lowercase() == "menunggu") {
                                Color.Yellow
                            } else {
                                Color.Green
                            }
                        )
                        .padding(6.dp)
                ) {
                    Text(
                        text = statusReservasi,
                        fontSize = 18.sp,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewReservasiBengkelItem(){
    TugasAkhirTheme {
        ReservasiBengkelItem(
            namaUser = "Naruto Uzumaki",
            numberUser = "081234567890",
            namaKaryawan = "Bambang",
            statusReservasi = "Menunggu",
            jenisKendalaReservasi = "Mesin",
            kendaraanReservasi = "Mobil",
            jamReservasi = "13.00 - 15.00",
            tanggalReservasi = "25-06-2024"
        )
    }
}