package com.example.tugasakhir.ui.screen.updateprioritas

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.BengkelModelFactory
import com.example.tugasakhir.ui.screen.updateprioritasharga.UpdatePrioritasHargaViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun UpdatePrioritasScreen(
    navigator: DestinationsNavigator,
    prioritasId: Int,
    bengkelsId: Int,
    bobot_nilai: Int,
    bobot_estimasi: Int,
    bobot_urgensi: Int,
    jenisKendaraan: String,
    jenisService: String,
    modifier: Modifier = Modifier,
    viewModel: UpdatePrioritasViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    )
){
    var bobotNilai by remember { mutableStateOf(bobot_nilai.toString()) }
    var bobotEstimasi by remember { mutableStateOf(bobot_estimasi.toString()) }
    var bobotUrgensi by remember { mutableStateOf(bobot_urgensi.toString()) }

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
                text = "Perbarui Prioritas Harga",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Jenis Kendaraan: $jenisKendaraan \nJenis Service: $jenisService" ,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = bobotNilai,
                onValueChange = { bobotNilai = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Nilai Prioritas Jenis Kerusakan",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Nilai Prioritas Jenis Kerusakan",
                        color = Color(0xFF86888D)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = bobotEstimasi,
                onValueChange = { bobotEstimasi = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Nilai Prioritas Estimasi Pengerjaan",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Nilai Prioritas Estimasi Pengerjaan",
                        color = Color(0xFF86888D)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = bobotUrgensi,
                onValueChange = { bobotUrgensi = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Nilai Prioritas Urgensi Kerusakan",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Nilai Prioritas Urgensi Kerusakan",
                        color = Color(0xFF86888D)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            Button(
                onClick = {
                    if(bobotNilai.isBlank() || bobotUrgensi.isBlank() || bobotUrgensi.isBlank()){
                        Toast.makeText(context, "Harap masukan semua data terlebih dahulu", Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.updatePrioritas(bengkelsId, prioritasId, bobotNilai.toInt(), bobotEstimasi.toInt(), bobot_urgensi.toInt())
                        Toast.makeText(context, "Berhasil memperbarui data Prioritas Harga", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Perbarui Prioritas Harga"
                )
            }
        }
    }
}