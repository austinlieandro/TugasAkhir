package com.example.tugasakhir.ui.components.bengkel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
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
fun BengkelItem(
    namaBengkel: String,
    lokasiBengkel: String,
    alamatBengkel: String,
    numberBengkel: String,
    modifier: Modifier = Modifier
){
    Card(
        colors = CardDefaults.cardColors(Color.White),
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .shadow(12.dp, RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = modifier,
        ) {
            Text(
                text = namaBengkel,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Text(
                text = "$lokasiBengkel, $alamatBengkel",
                maxLines = 1,
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
                    .padding(8.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd,
            ) {
                Row {
                    Text(
                        text = "Detail Bengkel",
                        fontSize = 18.sp,
                    )
                    Icon(
                        imageVector = Icons.Default.NavigateNext,
                        contentDescription = "Detail Bengkel"
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewBengkelItem() {
    TugasAkhirTheme {
        BengkelItem(
            namaBengkel = "Bengkel Motor Nusantara",
            lokasiBengkel = "Jakarta",
            alamatBengkel = "Jl. Konoha",
            numberBengkel = "081234567890",
        )
    }
}
