package com.example.tugasakhir.ui.screen.inputjenislayanan

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.BengkelModelFactory
import com.example.tugasakhir.data.factory.UserModelFactory
import com.example.tugasakhir.ui.screen.jenisservice.JenisServiceViewModel
import com.example.tugasakhir.ui.theme.TugasAkhirTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.DasbboardScreenDestination
import com.ramcosta.composedestinations.generated.destinations.InputKaryawanScerenDestination
import com.ramcosta.composedestinations.generated.destinations.JamOperasionalScreenDestination
import com.ramcosta.composedestinations.generated.destinations.JenisLayananScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputJenisLayananScreen(
    navigator: DestinationsNavigator,
    jenis: String,
    bengkelId: Int,
    modifier: Modifier = Modifier,
    viewModel: InputJenisLayananViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    ),
    layananViewModel: JenisServiceViewModel = viewModel(
        factory = UserModelFactory.getInstance(LocalContext.current)
    )
) {
    LaunchedEffect(Unit) {
        layananViewModel.getJenisService()
    }

    val localFocusManager = LocalFocusManager.current
    val context = LocalContext.current

    var namaLayanan by remember { mutableStateOf("") }
    var hargaLayanan by remember { mutableStateOf("") }

    var selectedLayanan = remember { mutableStateListOf<String>() }

    val jenisSeviceListState by layananViewModel.jenisSeviceList.observeAsState(emptyList())

    jenisSeviceListState?.let { serviceList ->
        serviceList.forEach { service ->
            service?.let {
                val layanan = it.namaService
                val isChecked = layanan?.let { it1 -> layananViewModel.isServiceChecked(it1) }

                if (isChecked == true && !selectedLayanan.contains(layanan)) {
                    selectedLayanan.add(layanan)
                } else if (!isChecked!! && selectedLayanan.contains(layanan)) {
                    selectedLayanan.remove(layanan)
                }
            }
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Jenis Layanan",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
            )
            OutlinedTextField(
                value = namaLayanan,
                onValueChange = { namaLayanan = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Nama Layanan",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Nama Layanan",
                        color = Color(0xFF86888D)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    containerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(color = Color.White)
                        .padding(8.dp)
                ) {
                    Column {
                        Text(
                            text = "Jenis Layanan",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        )
                        jenisSeviceListState?.forEach { service ->
                            service?.let {
                                val layanan = it.namaService
                                val isChecked = it.isChecked
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                ) {
                                    Checkbox(
                                        checked = isChecked,
                                        onCheckedChange = { checked ->
                                            layanan?.let { layananName ->
                                                layananViewModel.setServiceChecked(layananName, checked)
                                                if (checked && !selectedLayanan.contains(layananName)) {
                                                    selectedLayanan.add(layananName)
                                                } else if (!checked && selectedLayanan.contains(layananName)) {
                                                    selectedLayanan.remove(layananName)
                                                }
                                            }
                                        }
                                    )
                                    Text(text = layanan ?: "", fontSize = 14.sp)
                                }
                            }
                        }
                    }
                }
            }
            OutlinedTextField(
                value = hargaLayanan,
                onValueChange = { hargaLayanan = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Harga Layanan",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Harga Layanan",
                        color = Color(0xFF86888D)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    containerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
            )
            Button(
                onClick = {
                    if(namaLayanan.isBlank() || selectedLayanan.isEmpty() || hargaLayanan.isBlank()){
                        Toast.makeText(context, "Data Tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.inputJenisLayanan(namaLayanan, selectedLayanan, hargaLayanan.toInt(), bengkelId)
                        navigator.navigate(JenisLayananScreenDestination(jenis, bengkelId))
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Red),
                shape = RoundedCornerShape(10.dp),
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Tambah Jenis Layanan"
                )
            }
        }
    }
}