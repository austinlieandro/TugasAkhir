package com.example.tugasakhir.ui.components.karyawan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
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

@Composable
fun KaryawanItem(
    namaKaryawan: String,
    modifier: Modifier = Modifier
){
    Card(
        colors = CardDefaults.cardColors(Color.White),
        modifier = modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = namaKaryawan,
                color = colorScheme.onSurface,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(8.dp, 0.dp, 8.dp, 0.dp)
            )
            Box(
                modifier = modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Navigate to List Kendaraan",
                    tint = colorScheme.onSurface,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewKaryawanItem(){
    TugasAkhirTheme {
        KaryawanItem("Ujang")
    }
}