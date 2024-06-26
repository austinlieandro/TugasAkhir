package com.example.tugasakhir.ui.screen.jamoperasional

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateListOf
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
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.InputKaryawanScerenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JamOperasionalScreen(
    navigator: DestinationsNavigator,
    bengkelId: Int,
    modifier: Modifier = Modifier,
    viewModel: JamOperasionalViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore),
){
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))

    var jamOperasionalMulai = remember { mutableStateListOf("") }
    var jamOperasionalSelesai = remember { mutableStateListOf("") }
    var listHari = remember { mutableStateListOf<String>() }

    val jamOperasional = mutableListOf<String>()
    val hari = mutableListOf<String>()
    val listSlot = mutableListOf<Int>()

    var isExpendedHari by remember { mutableStateOf(false) }
    var selectedHari by remember { mutableStateOf("") }

    var slot = remember { mutableStateListOf("") }

    val detailBengkelState = viewModel.detailBengkel.observeAsState()

    val context = LocalContext.current

    LaunchedEffect(userModel.id) {
        viewModel.detailBengkel(userModel.id, bengkelId)
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
                                    listHari.clear()
                                    repeat(jamOperasionalMulai.size){
                                        listHari.add(selectedHari)
                                    }
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
                Column(modifier = Modifier.weight(1f)) {
                    jamOperasionalMulai.forEachIndexed { index, textFieldValue ->
                        OutlinedTextField(
                            value = textFieldValue,
                            onValueChange = { newValue ->
                                jamOperasionalMulai[index] = newValue
                            },
                            label = { Text("Jam Mulai") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        )
                    }
                }
                Column(modifier = modifier.weight(1f)) {
                    jamOperasionalSelesai.forEachIndexed { index, textFieldValue ->
                        OutlinedTextField(
                            value = textFieldValue,
                            onValueChange = { newValue ->
                                jamOperasionalSelesai[index] = newValue
                            },
                            label = { Text("Jam Selesai") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        )
                    }
                }
                Column(modifier = Modifier.weight(1f)) {
                    slot.forEachIndexed { index, textFieldValue ->
                        OutlinedTextField(
                            value = textFieldValue,
                            onValueChange = { newValue ->
                                slot[index] = newValue
                            },
                            label = { Text("Slot") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        )
                    }
                }
            }
            Button(
                onClick = {
                    jamOperasionalMulai.add("")
                    jamOperasionalSelesai.add("")
                    slot.add("")
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Tambah Jam Operasional")
            }
            Button(
                onClick = {
                    jamOperasionalMulai.removeLast()
                    jamOperasionalSelesai.removeLast()
                    slot.removeLast()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Kurangi Jam Operasional")
            }
            Button(
                onClick = {
                    repeat(jamOperasionalMulai.size){ index ->
                        val jamMulai = jamOperasionalMulai[index]
                        val jamSelesai = jamOperasionalSelesai[index]
                        jamOperasional.add("$jamMulai - $jamSelesai")
                    }

                    repeat(jamOperasionalMulai.size){
                        hari.add(selectedHari)
                    }
                    slot.forEach { data ->
                        val intValue = data.toIntOrNull()
                        if (intValue != null)
                        {
                            listSlot.add(intValue)
                        }
                    }

                    jamOperasionalMulai.clear()
                    jamOperasionalSelesai.clear()
                    slot.clear()
                    listHari.clear()
                    selectedHari = ""
                    Toast.makeText(context, "Data pada hari $selectedHari telah disimpan", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Simpan Data Hari ini")
            }
            Button(
                onClick = {
                    viewModel.daftarJamOperasional(jamOperasional, hari, listSlot, bengkelId)
                    navigator.navigate(InputKaryawanScerenDestination(bengkelId = bengkelId))
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Simpan Semua Data Jam Operasional")
            }
        }
    }
}