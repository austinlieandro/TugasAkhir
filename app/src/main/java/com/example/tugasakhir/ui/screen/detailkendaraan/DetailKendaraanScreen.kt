package com.example.tugasakhir.ui.screen.detailkendaraan

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.UserModelFactory
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKendaraanScreen(
    navigator: DestinationsNavigator,
    kendaraanId: Int,
    usersId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailKendaraanViewModel = viewModel(
        factory = UserModelFactory.getInstance(LocalContext.current)
    )
) {
    val statusState by viewModel.status.observeAsState(false)
    val detailKendaraanState by viewModel.detailKendaraan.observeAsState()
    var platKendaraan by remember { mutableStateOf("") }
    val merekKendaraanState by viewModel.merekKendaraanList.observeAsState()
    var isExpendedMerek by remember { mutableStateOf(false) }
    var selectedTextMerek by remember { mutableStateOf("") }
    var idMerekKendaraan by remember { mutableStateOf(0) }

    val localFocusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(detailKendaraanState) {
        detailKendaraanState?.let { detail ->
            platKendaraan = detail.platKendaraan ?: ""
        }
    }
    LaunchedEffect(merekKendaraanState) {
        merekKendaraanState?.let { data ->
            val selectedMerek = data.find { it?.id == detailKendaraanState?.merekKendaraanId }
            selectedTextMerek = selectedMerek?.merekKendaraan ?: ""
            idMerekKendaraan = selectedMerek?.id ?: 0
        }
    }

    LaunchedEffect(Unit) {
        viewModel.detailKendaraanUser(usersId, kendaraanId)
        viewModel.getMerekKendaraan()
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (!statusState){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }else{
            Column(
                modifier = modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = "Update Kendaraan",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = "Jenis Kendaraan: ${detailKendaraanState?.jenisKendaraan}",
                    modifier = modifier
                        .padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = platKendaraan,
                    onValueChange = { platKendaraan = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Plat Kendaraan",
                            color = Color(0xFF86888D)
                        )
                    },
                    label = {
                        Text(
                            text = "Plat Kendaraan",
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
                ExposedDropdownMenuBox(
                    expanded = isExpendedMerek,
                    onExpandedChange = { isExpendedMerek = !isExpendedMerek }
                ) {
                    TextField(
                        value = selectedTextMerek ?: "",
                        onValueChange = {},
                        readOnly = true,
                        shape = RoundedCornerShape(10.dp),
                        label = { Text("Pilih Merek Kendaraan") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.outline,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            containerColor = Color.White,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpendedMerek)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 16.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = isExpendedMerek,
                        onDismissRequest = { isExpendedMerek = false },
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        merekKendaraanState?.filter { it?.jenisKendaraan == detailKendaraanState?.jenisKendaraan }?.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(text = option?.merekKendaraan ?: "") },
                                onClick = {
                                    selectedTextMerek = option?.merekKendaraan ?: ""
                                    idMerekKendaraan = option?.id ?: 0
                                    isExpendedMerek = false
                                },
                                modifier = Modifier
                                    .background(Color.White)
                            )
                        }
                    }
                }
                Button(
                    onClick = {
                        if (platKendaraan.isBlank() || selectedTextMerek.isBlank()){
                            Toast.makeText(context, "Harap masukan semua data terlebih dahulu", Toast.LENGTH_SHORT).show()
                        }else{
                            viewModel.updateKendaraanUser(usersId, kendaraanId, platKendaraan, idMerekKendaraan)
                            Toast.makeText(context, "Berhasil update data kendaraan", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Update Kendaraan"
                    )
                }
                Button(
                    onClick = {
                        viewModel.deleteKendaraanUser(usersId, kendaraanId)
                        Toast.makeText(context, "Berhasil hapus kendaraan", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Hapus Kendaraan"
                    )
                }
            }
        }
    }
}
