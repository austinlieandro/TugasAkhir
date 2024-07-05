package com.example.tugasakhir.ui.components.merekkendaraan

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

@Composable
fun MerekKendaraanItem(
    merekKendaraan: String,
    jenisKendaraan: String,
    modifier: Modifier = Modifier
){
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = merekKendaraan,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        modifier = modifier
                            .width(300.dp)
                    )
                    Text(
                        text = jenisKendaraan,
                    )
                }
                Box(
                    contentAlignment = Alignment.CenterEnd,
                    modifier = modifier
                        .fillMaxWidth()
                ){
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Merek Kendaraan"
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewMerekKendaraan(){
    TugasAkhirTheme {
        MerekKendaraanItem(
            "Daihatsu",
            "Mobil"
        )
    }
}