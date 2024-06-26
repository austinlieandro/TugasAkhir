package com.example.tugasakhir.ui.screen.updatejamoperasional

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Destination<RootGraph>
@Composable
fun UpdateOperasionalScreen(
    navigator: DestinationsNavigator,
    bengkelId: Int,
    jamId: Int,
    hariOperasional: String,
    jamOperasional: String,
    slot: Int,
    modifier: Modifier = Modifier,
    viewModel: UpdateOperasionalViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    )
){
    val jamParts = jamOperasional.split(" - ")
    val jamMulaiInitial = if (jamParts.isNotEmpty()) jamParts[0].trim() else ""
    val jamSelesaiInitial = if (jamParts.size > 1) jamParts[1].trim() else ""

    var jamMulai by remember { mutableStateOf(jamMulaiInitial) }
    var jamSelesai by remember { mutableStateOf(jamSelesaiInitial) }
    var slotState by remember { mutableStateOf(slot.toString()) }
    var updatedJamOperasional by remember { mutableStateOf("") }

    val context = LocalContext.current

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Edit Jam Operasional",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
            )
            Text(
                text = hariOperasional,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = modifier
                    .padding(top = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = jamMulai,
                        onValueChange = { jamMulai = it },
                        label = { Text("Jam Mulai") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                }
                Column(
                    modifier = modifier.weight(1f)
                ){
                    OutlinedTextField(
                        value = jamSelesai,
                        onValueChange = { jamSelesai = it },
                        label = { Text("Jam Selesai") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                }
                Column(
                    modifier = modifier.weight(1f)
                ){
                    OutlinedTextField(
                        value = slotState,
                        onValueChange = { slotState = it },
                        label = { Text("Slot") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                }
            }
            Button(
                onClick = {
                    updatedJamOperasional = "$jamMulai - $jamSelesai"
                    viewModel.updateJamOperasional(bengkelId, jamId, updatedJamOperasional, slotState.toInt())
                    Toast.makeText(context, "Berhasil Update Jam Operasional", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Simpan Perubahan")
            }
        }
    }
}