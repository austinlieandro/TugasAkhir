package com.example.tugasakhir.ui.components.jenislayanan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugasakhir.ui.theme.TugasAkhirTheme
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

@Composable
fun JenisLayananItem(
    namaLayanan: String,
    jenisLayanan: List<String?>,
    hargaLayanan: Int,
    modifier: Modifier = Modifier
){
    val formatRupiah = (NumberFormat.getCurrencyInstance(Locale("in", "ID")) as DecimalFormat).apply {
        maximumFractionDigits = 0
    }
    val hargaLayananFormatted = formatRupiah.format(hargaLayanan)
    Card(
        colors = CardDefaults.cardColors(Color.White),
        modifier = modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(12.dp))
    ){
        Column(
            modifier = modifier
                .padding(16.dp)
        ) {
            Text(
                text = namaLayanan,
                fontSize = 24.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
            Row {
                Text(
                    text = jenisLayanan.joinToString(separator = ", "),
                    maxLines = 1,
                    modifier = modifier
                        .padding(vertical = 8.dp)
                        .width(325.dp)
                )
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ){
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Jenis Layanan"
                    )
                }
            }
            Text(
                text = hargaLayananFormatted
            )
        }
    }
}

@Preview
@Composable
fun PreviewJenisLayanan(){
    TugasAkhirTheme {
        JenisLayananItem(
            "Bundle A",
            listOf("AKI", "MESIN"),
            1000000
        )
    }
}