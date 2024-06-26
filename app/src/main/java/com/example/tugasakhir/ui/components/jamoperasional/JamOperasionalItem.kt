package com.example.tugasakhir.ui.components.jamoperasional

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.tugasakhir.ui.theme.TugasAkhirTheme

@Composable
fun JamOperasionalItem(
    jam: String,
    hari: String,
    slot: Int,
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
            Text(
                text = "Hari operasional: $hari",
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(bottom = 8.dp)
            )
            Row {
                Column {
                    Text(
                        text = "Jam: $jam",
                        modifier = modifier
                            .padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Slot: $slot",
                    )
                }
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = modifier
                        .fillMaxWidth()
                ){
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Jam Operasional"
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewJamOperasionalItem(){
    TugasAkhirTheme {
        JamOperasionalItem(jam = "15.00 - 17.00", hari = "Senin", slot = 2)
    }
}