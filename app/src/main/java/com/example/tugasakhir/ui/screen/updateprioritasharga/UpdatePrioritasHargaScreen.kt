package com.example.tugasakhir.ui.screen.updateprioritasharga

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.BengkelModelFactory
import com.example.tugasakhir.data.factory.UserModelFactory
import com.example.tugasakhir.ui.screen.updatemerekkendaraan.UpdateMerekKendaraanViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePrioritasHargaScreen(
    navigator: DestinationsNavigator,
    harga: Int,
    bobotNilai: Int,
    bengkelsId: Int,
    prioritasId: Int,
    modifier: Modifier = Modifier,
    viewModel: UpdatePrioritasHargaViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    )
){
    var hargaState by remember { mutableStateOf(harga.toString()) }
    var nilai by remember { mutableStateOf(bobotNilai.toString()) }

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
            OutlinedTextField(
                value = hargaState,
                onValueChange = { hargaState = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Harga",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Harga",
                        color = Color(0xFF86888D)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            OutlinedTextField(
                value = nilai,
                onValueChange = { nilai = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Bobot Nilai",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Bobot Nilai",
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
                    if(hargaState.isBlank() || nilai.isBlank()){
                        Toast.makeText(context, "Harap masukan semua data terlebih dahulu", Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.updatePrioritasHarga(bengkelsId, prioritasId, hargaState.toInt(), nilai.toInt())
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