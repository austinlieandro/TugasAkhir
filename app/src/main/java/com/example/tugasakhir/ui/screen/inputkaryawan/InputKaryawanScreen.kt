package com.example.tugasakhir.ui.screen.inputkaryawan

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.KaryawanModelFactory
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.BengkelScreenDestination
import com.ramcosta.composedestinations.generated.destinations.InputJenisLayananScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputKaryawanSceren(
    bengkelId: Int,
    jenisDaftar: String,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: InputKaryawanViewModel = viewModel(
        factory = KaryawanModelFactory.getInstance(LocalContext.current)
    )
){
    val statusState by viewModel.status.observeAsState(false)
    var namaKaryawan by remember { mutableStateOf("") }
    val context = LocalContext.current
    val openAlertDialog = remember {mutableStateOf(false)}

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
                text = "Input Karyawan",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 16.dp)
            )
            OutlinedTextField(
                value = namaKaryawan,
                onValueChange = { namaKaryawan = it },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Nama Karyawan",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Nama Karyawan",
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
                    if (namaKaryawan.isBlank()){
                        Toast.makeText(context, "Nama karyawan tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.inputKaryawan(namaKaryawan, bengkelId)
                        Toast.makeText(context, "Berhasil menambahkan karyawan", Toast.LENGTH_SHORT).show()
                        namaKaryawan = ""
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Simpan Data Karyawan")
            }
            Button(
                onClick = {
                    openAlertDialog.value = true
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Selesai Menyimpan Data Karyawan")
            }
            if (openAlertDialog.value){
                AlertDialog(
                    onDismissRequest = { openAlertDialog.value = false },
                    title = {
                        Text(text = "Warning")
                    },
                    text = {
                        Text(text = "Apakah kamu sudah selesai input karyawan?")
                    },
                    confirmButton = {
                        Button(
                            shape = RoundedCornerShape(10.dp),
                            onClick = {
                                openAlertDialog.value = false
                                navigator.navigate(InputJenisLayananScreenDestination("daftar", bengkelId))
                            }
                        ) {
                            Text(
                                text = "Ya"
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openAlertDialog.value = false
                            }
                        ) {
                            Text(
                                text = "Belum"
                            )
                        }
                    }
                )
            }
        }
    }
}