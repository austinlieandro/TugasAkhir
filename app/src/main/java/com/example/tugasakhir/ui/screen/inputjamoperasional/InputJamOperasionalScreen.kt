package com.example.tugasakhir.ui.screen.inputjamoperasional

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.BengkelModelFactory
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.example.tugasakhir.ui.screen.jamoperasional.JamOperasionalViewModel
import com.example.tugasakhir.ui.theme.TugasAkhirTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputJamOperasionalScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: InputJamOperasionalViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore),
){
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))
    val detailBengkelState = viewModel.detailBengkel.observeAsState()
    val statusState by viewModel.status.observeAsState(false)
    var jamOperasionalMulai by remember { mutableStateOf("") }
    var jamOperasionalSelesai by remember { mutableStateOf("") }
    var slot by remember { mutableStateOf("") }
    var isExpendedHari by remember { mutableStateOf(false) }
    var selectedHari by remember { mutableStateOf("") }

    val context = LocalContext.current

    fun isValidTimeFormat(time: String): Boolean {
        val regex = Regex(pattern = """\d{2}\.\d{2}""")
        return time.matches(regex)
    }

    LaunchedEffect(userModel.id) {
        viewModel.detailBengkel(userModel.id, userModel.bengkels_id, "", "")
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ){
        if (!statusState) {
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
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Jam Operasional",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = isExpendedHari,
                    onExpandedChange = { isExpendedHari = !isExpendedHari }
                ) {
                    TextField(
                        value = selectedHari,
                        onValueChange = {},
                        readOnly = true,
                        shape = RoundedCornerShape(10.dp),
                        label = {
                            Text(text = "Pilih Hari")
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            containerColor = Color.White,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpendedHari)
                        },
                        modifier = modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = isExpendedHari,
                        onDismissRequest = { isExpendedHari = false },
                        modifier = modifier
                            .background(Color.White)
                    ) {
                        detailBengkelState.value?.hariOperasional?.forEach { option ->
                            DropdownMenuItem(
                                text = { option?.let { Text(it) } },
                                onClick = {
                                    option?.let {
                                        selectedHari = it
                                    }
                                    isExpendedHari = false
                                },
                                modifier = modifier
                                    .background(Color.White)
                            )
                        }
                    }
                }
                Text(
                    text = "Masukan format jam mulai dan selesai seperti berikut: \nJam mulai: 11.00 \nJam selesai: 13.00",
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
                        modifier = modifier
                            .weight(1f)
                    ) {
                        OutlinedTextField(
                            value = jamOperasionalMulai,
                            onValueChange = { jamOperasionalMulai = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                            label = { Text("Jam Mulai") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        )
                    }
                    Column(
                        modifier = modifier
                            .weight(1f)
                    ) {
                        OutlinedTextField(
                            value = jamOperasionalSelesai,
                            onValueChange = { jamOperasionalSelesai = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                            label = { Text("Jam Selesai") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        )
                    }
                    Column(
                        modifier = modifier
                            .weight(1f)
                    ) {
                        OutlinedTextField(
                            value = slot,
                            onValueChange = { slot = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ),
                            label = { Text("Slot") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        )
                    }
                }
                Button(
                    onClick = {
                        var isValid = true
                        if (!isValidTimeFormat(jamOperasionalMulai)){
                            isValid = false
                            Toast.makeText(context, "Format jam mulai tidak valid: $jamOperasionalMulai", Toast.LENGTH_SHORT).show()
                        }
                        if (!isValidTimeFormat(jamOperasionalSelesai)){
                            isValid = false
                            Toast.makeText(context, "Format jam selesai tidak valid: $jamOperasionalSelesai", Toast.LENGTH_SHORT).show()
                        }
                        if (isValid){
                            val jamOperasional = "$jamOperasionalMulai - $jamOperasionalSelesai"
                            if (jamOperasional.isEmpty()) {
                                Toast.makeText(context, "Silahkan tambah jam operasional terlebih dahulu", Toast.LENGTH_SHORT).show()
                            }else{
                                viewModel.inputJamOperasional(jamOperasional, selectedHari, slot.toInt(), userModel.bengkels_id)
                                Toast.makeText(context, "Berhasil menambah jam operasional", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Simpan data jam operasional"
                    )
                }
            }
        }
    }
}