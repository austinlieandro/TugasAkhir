package com.example.tugasakhir.ui.screen.inputprioritasharga

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.BengkelModelFactory
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun InputPrioritasHargaScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: InputPrioritasHargaViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore),
){
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))
    val statusState by viewModel.status.observeAsState(false)
    var listHarga = mutableListOf<Int>()
    var listBobotNilai = mutableListOf<Int>()

    var harga = remember { mutableStateListOf("") }
    var bobotNilai = remember { mutableStateListOf("") }

    val context = LocalContext.current

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ){
            Text(
                text = "Memasukan Data Prioritas Harga",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(start = 8.dp, end = 8.dp)
            )
            Text(
                text = "Masukan format harga dan bobot nilai seperti berikut: \nHarga: 100000 - Bobot Nilai: 5\nHarga: 200000 - Bobot Nilai: 4" +
                        "\n\nMaksud dari harga adalah jika harganya 100000 maka dalam rentang 0 - 100000 bobot nilainya adalah 5" +
                        "\nlalu rentang harga 100001 - 200000 bobot nilainya 4." +
                        "\nSemakin kecil bobot nilainya maka semakin tinggi prioritasnya",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
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
                    harga.forEachIndexed{index, textFieldValue ->
                        OutlinedTextField(
                            value = textFieldValue,
                            onValueChange = { newValue ->
                                harga[index] = newValue
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                            label = { Text("Harga") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        )
                    }
                }
                Column(
                    modifier = modifier.weight(1f)
                ) {
                    bobotNilai.forEachIndexed{index, textFieldValue ->
                        OutlinedTextField(
                            value = textFieldValue,
                            onValueChange = { newValue ->
                                bobotNilai[index] = newValue
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                            label = { Text("Bobot Nilai") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        )
                    }
                }
            }
            Button(
                onClick = {
                    harga.add("")
                    bobotNilai.add("")
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Tambah Prioritas Harga"
                )
            }
            Button(
                onClick = {
                    harga.removeLast()
                    bobotNilai.removeLast()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Kurangi Prioritas Harga"
                )
            }
            Button(
                onClick = {
                    harga.forEach { data ->
                        val intValue = data.toIntOrNull()
                        if (intValue != null){
                            listHarga.add(intValue)
                        }
                    }
                    bobotNilai.forEach {data ->
                        val intValue = data.toIntOrNull()
                        if (intValue != null){
                            listBobotNilai.add(intValue)
                        }
                    }
                    if (listHarga.isEmpty() || listBobotNilai.isEmpty()){
                        Toast.makeText(context, "Harap isi semua data terlebih dahului", Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.inputPrioritasHarga(listHarga, listBobotNilai, userModel.bengkels_id)
                        Toast.makeText(context, "Berhasil menambah prioritas Harga", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Simpan Prioritas Harga"
                )
            }
        }
    }
}