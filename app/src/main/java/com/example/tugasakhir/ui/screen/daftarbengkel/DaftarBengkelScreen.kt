package com.example.tugasakhir.ui.screen.daftarbengkel

import android.os.Handler
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import com.ramcosta.composedestinations.generated.destinations.InputJenisLayananScreenDestination
import com.ramcosta.composedestinations.generated.destinations.JamOperasionalScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarBengkelScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: DaftarBengkelViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore),
) {
    val statusState by viewModel.status.observeAsState(false)
    val daftarBengkelState = viewModel.daftarBengkel.observeAsState()

    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))

    val localFocusManager = LocalFocusManager.current
    val context = LocalContext.current

    var namaBengkel by remember { mutableStateOf("") }
    var lokasiBengkel by remember { mutableStateOf("") }
    var numberBengkel by remember { mutableStateOf("") }
    var alamatBengkel by remember { mutableStateOf("") }
    var gmapsBengkel by remember { mutableStateOf("") }

    var isMotorChecked by remember { mutableStateOf(false) }
    var isMobilChecked by remember { mutableStateOf(false) }
    var selectedKendaraan by remember { mutableStateOf(mutableListOf<String>()) }

    var isSeninChecked by remember { mutableStateOf(false) }
    var isSelasaChecked by remember { mutableStateOf(false) }
    var isRabuChecked by remember { mutableStateOf(false) }
    var isKamisChecked by remember { mutableStateOf(false) }
    var isJumatChecked by remember { mutableStateOf(false) }
    var isSabtuChecked by remember { mutableStateOf(false) }
    var isMingguChecked by remember { mutableStateOf(false) }
    var selectedHari by remember { mutableStateOf(mutableListOf<String>()) }

    var pickedTimeBuka by remember { mutableStateOf(LocalTime.NOON) }
    val formattedTimeBuka by remember { derivedStateOf {
        DateTimeFormatter
            .ofPattern("HH.mm")
            .format(pickedTimeBuka)
    } }

    var pickedTimeTutup by remember { mutableStateOf(LocalTime.NOON) }
    val formattedTimeTutup by remember { derivedStateOf {
        DateTimeFormatter
            .ofPattern("HH.mm")
            .format(pickedTimeTutup)
    } }

    if (isMobilChecked && !selectedKendaraan.contains("mobil")) {
        selectedKendaraan.add("mobil")
    } else if (!isMobilChecked && selectedKendaraan.contains("mobil")) {
        selectedKendaraan.remove("mobil")
    }

    if (isMotorChecked && !selectedKendaraan.contains("motor")) {
        selectedKendaraan.add("motor")
    } else if (!isMotorChecked && selectedKendaraan.contains("motor")) {
        selectedKendaraan.remove("motor")
    }

    val hariMap = mapOf(
        "senin" to isSeninChecked,
        "selasa" to isSelasaChecked,
        "rabu" to isRabuChecked,
        "kamis" to isKamisChecked,
        "jumat" to isJumatChecked,
        "sabtu" to isSabtuChecked,
        "minggu" to isMingguChecked
    )

    hariMap.forEach { (hari, isChecked) ->
        if (isChecked && !selectedHari.contains(hari)) {
            selectedHari.add(hari)
        } else if (!isChecked && selectedHari.contains(hari)) {
            selectedHari.remove(hari)
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
                text = "Daftar Bengkel",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = namaBengkel,
                onValueChange = { namaBengkel = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Nama Bengkel",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Nama Bengkel",
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
                value = lokasiBengkel,
                onValueChange = { lokasiBengkel = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Domisili Bengkel",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Domisili Bengkel",
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
                value = numberBengkel,
                onValueChange = { numberBengkel = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Nomor Telepon Bengkel",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Nomor Telepon Bengkel",
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
                value = alamatBengkel,
                onValueChange = { alamatBengkel = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Alamat Bengkel",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Alamat Bengkel",
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
                value = gmapsBengkel,
                onValueChange = { gmapsBengkel = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Google Maps Link",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Google Maps Link",
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
            Row(
                modifier = Modifier
                    .padding(top = 14.dp)
                    .fillMaxWidth()
            ){
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
                ){
                    Column {
                        Text(
                            text = "Jenis Kendaraan Yang Dilayani",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Checkbox(
                                checked = isMotorChecked,
                                onCheckedChange = { isMotorChecked = it }
                            )
                            Text(text = "Motor")
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = modifier
                                    .fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Checkbox(
                                        checked = isMobilChecked,
                                        onCheckedChange = { isMobilChecked = it },
                                    )
                                    Text(text = "Mobil")
                                }
                            }
                        }
                    }

                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 14.dp)
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
                            text = "Hari Operasional",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        )
                        listOf(
                            "Senin" to isSeninChecked,
                            "Selasa" to isSelasaChecked,
                            "Rabu" to isRabuChecked,
                            "Kamis" to isKamisChecked,
                            "Jumat" to isJumatChecked,
                            "Sabtu" to isSabtuChecked,
                            "Minggu" to isMingguChecked
                        ).forEach { (label, isChecked) ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(bottom = 8.dp)
                            ) {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = {
                                        when (label) {
                                            "Senin" -> isSeninChecked = it
                                            "Selasa" -> isSelasaChecked = it
                                            "Rabu" -> isRabuChecked = it
                                            "Kamis" -> isKamisChecked = it
                                            "Jumat" -> isJumatChecked = it
                                            "Sabtu" -> isSabtuChecked = it
                                            "Minggu" -> isMingguChecked = it
                                        }
                                    }
                                )
                                Text(text = label, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
            val timeDialogStateBuka = rememberMaterialDialogState()
            OutlinedTextField(
                value = formattedTimeBuka,
                onValueChange = {},
                singleLine = true,
                readOnly = true,
                enabled = false,
                label = {
                    Text(
                        text = "Pilih Jam Buka",
                        color = Color(0xFF86888D)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = Color.Black,
                    disabledTextColor = Color.Black,
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .clickable {
                        timeDialogStateBuka.show()
                    }
            )
            MaterialDialog(
                dialogState = timeDialogStateBuka,
                buttons = {
                    positiveButton("Ok")
                    negativeButton("Cancel")
                }
            ){
                timepicker(
                    initialTime = LocalTime.NOON,
                    title = "Pilih Jam",
                ){
                    pickedTimeBuka = it
                }
            }
            val timeDialogStateTutup = rememberMaterialDialogState()
            OutlinedTextField(
                value = formattedTimeTutup,
                onValueChange = {},
                singleLine = true,
                readOnly = true,
                enabled = false,
                label = {
                    Text(
                        text = "Pilih Jam Tutup",
                        color = Color(0xFF86888D)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledBorderColor = Color.Black,
                    disabledTextColor = Color.Black,
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .clickable {
                        timeDialogStateTutup.show()
                    }
            )
            MaterialDialog(
                dialogState = timeDialogStateTutup,
                buttons = {
                    positiveButton("Ok")
                    negativeButton("Cancel")
                }
            ){
                timepicker(
                    initialTime = LocalTime.NOON,
                    title = "Pilih Jam",
                ){
                    pickedTimeTutup = it
                }
            }

            Button(
                onClick = {
                    if (namaBengkel.isBlank() || lokasiBengkel.isBlank() || numberBengkel.isBlank() ||
                        alamatBengkel.isBlank() || selectedKendaraan.isEmpty() || selectedHari.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Harap lengkapi semua kolom yang diperlukan",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.daftarBengkel(
                            namaBengkel,
                            lokasiBengkel,
                            numberBengkel,
                            alamatBengkel,
                            gmapsBengkel,
                            selectedKendaraan.toList(),
                            selectedHari.toList(),
                            formattedTimeBuka,
                            formattedTimeTutup,
                            userModel.id)
                        Toast.makeText(
                            context,
                            "Data berhasil disimpan",
                            Toast.LENGTH_SHORT
                        ).show()
                        Handler().postDelayed({
                            navigator.navigate(JamOperasionalScreenDestination(daftarBengkelState.value?.id ?: 0))
                        }, 500)
                    }
                },
                colors = ButtonDefaults.buttonColors(Color.Red),
                shape = RoundedCornerShape(10.dp),
                modifier = modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Daftar Bengkel",
                    color = Color.White
                )
            }
        }
    }
}
