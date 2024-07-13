package com.example.tugasakhir.ui.components.riwayat

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
fun RiwayatItem(
    namaBengkel: String,
    lokasiBengkel: String,
    alamatBengkel: String,
    numberBengkel: String,
    statusBengkel: String,
    tanggalReservasi: String,
    jamReservasi: String,
    gmapsBengkel: String,
    merekKendaraan: String,
    platKendaraan: String,
    jenisPerbaikan: String,
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(Color.White),
        modifier = modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = modifier,
        ) {
            Text(
                text = namaBengkel,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Text(
                text = "$lokasiBengkel, $alamatBengkel",
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp, bottom = 8.dp)
            )
            Text(
                text = numberBengkel,
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp)
            )
            Box(
                modifier = modifier
                    .padding(start = 16.dp, top = 8.dp)
                    .clickable {
                        openGmaps(context, gmapsBengkel)
                    }
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "GMAPS BENGKEL"
                    )
                    Text(
                        text = "Google Maps Location",
                        fontSize = 18.sp,
                    )
                }
            }
            Divider(
                color = Color.Black,
                modifier = modifier
                    .padding(vertical = 8.dp)
            )
            Text(
                text = "Merek Kendaraan: $merekKendaraan",
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp, bottom = 8.dp)
            )
            Text(
                text = "Jenis Perbaikan: $jenisPerbaikan",
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp, bottom = 8.dp)
            )
            Text(
                text = "Plat Kendaraan: $platKendaraan",
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp, bottom = 8.dp)
            )
            Text(
                text = "Tanggal: $tanggalReservasi",
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp, bottom = 8.dp)
            )
            Text(
                text = "Jam: $jamReservasi",
                fontSize = 18.sp,
                modifier = modifier
                    .padding(start = 16.dp)
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
                            if (statusBengkel.lowercase() == "menunggu" || statusBengkel.lowercase() == "dibatalkan" || statusBengkel.lowercase() == "relokasi") {
                                Color.Red
                            } else if(statusBengkel.lowercase() == "proses") {
                                Color.Yellow
                            }else{
                                Color.Green
                            }
                        )
                        .padding(6.dp)
                ) {
                    Text(
                        text = statusBengkel,
                        fontSize = 18.sp,
                        color =
                        if (statusBengkel.lowercase() == "menunggu" || statusBengkel.lowercase() =="dibatalkan" || statusBengkel.lowercase() == "relokasi") {
                            Color.White
                        } else{
                            Color.Black
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRiwayatItem() {
    TugasAkhirTheme {
        RiwayatItem(
            namaBengkel = "Bengkel Motor Nusantara",
            lokasiBengkel = "Jakarta",
            alamatBengkel = "Jl. Konoha",
            numberBengkel = "081234567890",
            statusBengkel = "Menunggu",
            tanggalReservasi = "22-06-2024",
            jamReservasi = "13.00 - 15.00",
            gmapsBengkel = "",
            "Honda",
            "L 5555 QM",
            "Mesin"
        )
    }
}

fun openGmaps(context: Context, link: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    context.startActivity(intent)
}