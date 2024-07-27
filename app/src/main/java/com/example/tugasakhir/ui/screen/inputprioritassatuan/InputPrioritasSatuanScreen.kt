package com.example.tugasakhir.ui.screen.inputprioritassatuan

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputPrioritasSatuanScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: InputPrioritasSatuanViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore),
){
    val errorState = viewModel.errorInput.observeAsState()
    val jenisServiceState = viewModel.jenisSeviceList.observeAsState()
    val detailBengkelState = viewModel.detailBengkel.observeAsState()
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))

    LaunchedEffect(userModel.id) {
        viewModel.getJenisService()
        viewModel.getDetailBengkel(userModel.id, userModel.bengkels_id, "", "")
    }

    var selectedTextJenisKendaraan by remember { mutableStateOf("") }
    var selectedTextJenisService by remember { mutableStateOf("") }

    var isExpendedKendaraan by remember { mutableStateOf(false) }
    var isExpendedJenisService by remember { mutableStateOf(false) }

    var bobotNilai by remember { mutableStateOf("") }
    var bobotEstimasi by remember { mutableStateOf("") }
    var bobotUrgensi by remember { mutableStateOf("") }

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
                text = "Masukan Data Prioritas Bengkel",
                fontSize = 24.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(bottom = 16.dp)
            )
            ExposedDropdownMenuBox(
                expanded = isExpendedKendaraan,
                onExpandedChange = { isExpendedKendaraan = !isExpendedKendaraan }
            ) {
                TextField(
                    value = selectedTextJenisKendaraan,
                    onValueChange = {},
                    readOnly = true,
                    shape = RoundedCornerShape(10.dp),
                    label = { Text("Jenis Kendaraan") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.outline,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        containerColor = Color.White,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpendedKendaraan)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )

                ExposedDropdownMenu(
                    expanded = isExpendedKendaraan,
                    onDismissRequest = { isExpendedKendaraan = false },
                    modifier = modifier
                        .background(Color.White)
                ) {
                    detailBengkelState.value?.jenisKendaraan?.forEach { option ->
                        DropdownMenuItem(
                            text = { option?.let { Text(it) } },
                            onClick = {
                                option?.let { selectedTextJenisKendaraan = it }
                                isExpendedKendaraan = false
                            },
                            modifier = modifier
                                .background(Color.White)
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = isExpendedJenisService,
                onExpandedChange = { isExpendedJenisService = !isExpendedJenisService }
            ) {
                TextField(
                    value = selectedTextJenisService,
                    onValueChange = {},
                    readOnly = true,
                    shape = RoundedCornerShape(10.dp),
                    label = { Text("Jenis Service") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.outline,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        containerColor = Color.White,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpendedJenisService)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )

                ExposedDropdownMenu(
                    expanded = isExpendedJenisService,
                    onDismissRequest = { isExpendedJenisService = false },
                    modifier = modifier
                        .background(Color.White)
                ) {
                    jenisServiceState.value?.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = "${option?.namaService}") },
                            onClick = {
                                selectedTextJenisService = option?.namaService ?: ""
                                isExpendedJenisService = false
                            },
                            modifier = modifier
                                .background(Color.White)
                        )
                    }
                }
            }
            Text(
                text = "Masukan Bobot Nilai. Semakin tinggi semakin diprioritaskan \n" +
                        "Range Bobot Nilai 1-10",
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(top = 16.dp)
            )
            OutlinedTextField(
                value = bobotNilai,
                onValueChange = { bobotNilai = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                label = { Text("Bobot jenis Kerusakan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            OutlinedTextField(
                value = bobotEstimasi,
                onValueChange = { bobotEstimasi = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                label = { Text("Bobot Estimasi Pengerjaan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            OutlinedTextField(
                value = bobotUrgensi,
                onValueChange = { bobotUrgensi = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                ),
                label = { Text("Bobot Urgensi Kerusakan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Button(
                onClick = {
                    if (selectedTextJenisKendaraan.isBlank() ||
                        selectedTextJenisService.isBlank() ||
                        bobotNilai.isBlank() ||
                        bobotUrgensi.isBlank() ||
                        bobotEstimasi.isBlank()){
                        Toast.makeText(context, "Harap isi semua data terlebih dahulu", Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.inputPrioritasSatuan(
                            selectedTextJenisKendaraan.toLowerCase(),
                            selectedTextJenisService.toLowerCase(),
                            bobotNilai.toInt(),
                            bobotEstimasi.toInt(),
                            bobotUrgensi.toInt(),
                            userModel.bengkels_id
                        )
                        if (viewModel.errorInput.value == null){
                            Toast.makeText(context, "Berhasil menambah prioritas", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "${viewModel.errorInput.value}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Simpan Prioritas"
                )
            }
        }
    }
}