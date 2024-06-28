package com.example.tugasakhir.ui.screen.inputkendaraan

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
import androidx.compose.runtime.collectAsState
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
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputKendaraanScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: InputKendaraanViewModel = viewModel(
        factory = UserModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore)
){
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))
    val statusState by viewModel.status.observeAsState(false)
    val listJenisKendaraan = remember { mutableListOf("Motor", "Mobil") }
    var isExpendedKendaraan by remember { mutableStateOf(false) }
    var selectedTextKendaraan by remember { mutableStateOf("") }
    var platKendaraan by remember { mutableStateOf("") }
    var merekKendaraan by remember { mutableStateOf("") }

    val localFocusManager = LocalFocusManager.current

    val context = LocalContext.current

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
                    text = "Input Kendaraan",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .padding(bottom = 16.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = isExpendedKendaraan,
                    onExpandedChange = { isExpendedKendaraan = !isExpendedKendaraan }
                ) {
                    TextField(
                        value = selectedTextKendaraan ?: "",
                        onValueChange = {},
                        readOnly = true,
                        shape = RoundedCornerShape(10.dp),
                        label = { Text("Pilih Jenis Kendaraan") },
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
                            .padding(top = 16.dp, bottom = 16.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = isExpendedKendaraan,
                        onDismissRequest = { isExpendedKendaraan = false },
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        listJenisKendaraan.forEach { option ->
                            DropdownMenuItem(
                                text = { option.let { Text(it) } },
                                onClick = {
                                    option.let { selectedTextKendaraan = it }
                                    isExpendedKendaraan = false
                                },
                                modifier = Modifier
                                    .background(Color.White)
                            )
                        }
                    }
                }
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
                OutlinedTextField(
                    value = merekKendaraan,
                    onValueChange = { merekKendaraan = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { localFocusManager.clearFocus() }
                    ),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Merek Kendaraan",
                            color = Color(0xFF86888D)
                        )
                    },
                    label = {
                        Text(
                            text = "Merek Kendaraan",
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
                        viewModel.inputKendaraan(selectedTextKendaraan, platKendaraan, merekKendaraan, userModel.id)
                        Toast.makeText(context, "Berhasil Menambahkan Kendaraan", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Tambah Kendaraan"
                    )
                }
            }
        }
    }
}