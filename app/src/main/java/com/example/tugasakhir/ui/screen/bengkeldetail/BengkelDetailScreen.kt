package com.example.tugasakhir.ui.screen.bengkeldetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.BengkelModelFactory
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.KonfirmasiScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.text.NumberFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.TextStyle
import java.util.Locale

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BengkelDetailScreen(
    navigator: DestinationsNavigator,
    userId: Int,
    bengkelId: Int,
    modifier: Modifier = Modifier,
    viewModel: BengkelDetailViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore),
) {
    val bengkelState = viewModel.detailBengkel.observeAsState()
    val slotState = viewModel.sisaSlot.observeAsState()
    val jenisLayananState = viewModel.jenisLayananBengkel.observeAsState()
    val statusState by viewModel.status.observeAsState(false)
    val statusFavorit = viewModel.statusFavorit.observeAsState()
    val kendaraanState = viewModel.kendaraanList.observeAsState()
    var holdFavorit by remember { mutableStateOf("") }
    var favorit by remember { mutableStateOf(holdFavorit == "1") }
    var jenisLayanan by remember { mutableStateOf("") }
    var hargaLayanan by remember { mutableStateOf(0) }

    val jamOperasionalState = viewModel.jamOperasionalList.observeAsState()
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))

    LaunchedEffect(userModel.id) {
        viewModel.getDetailBengkel(userId, bengkelId, "", "")
        viewModel.getKendaraan(userId)
    }

    LaunchedEffect(statusFavorit.value) {
        favorit = statusFavorit.value == "0"
    }

    val context = LocalContext.current

    var selectedTextKendaraan by remember { mutableStateOf("") }
    var selectedTextKendaraanUser by remember { mutableStateOf("") }
    var selectedTextLayanan by remember { mutableStateOf("") }
    var selectedTextJamOperasional by remember { mutableStateOf("") }

    var selectedHariOperasional by remember { mutableStateOf<String?>(null) }

    var isExpendedKendaraan by remember { mutableStateOf(false) }
    var isExpendedKendaraanUser by remember { mutableStateOf(false) }
    var isExpendedLayanan by remember { mutableStateOf(false) }
    var isExpendedJamOperasional by remember { mutableStateOf(false) }

    var idSelectedKendaraanUser by remember { mutableStateOf(0) }

    val localFocusManager = LocalFocusManager.current

    var kendala by remember { mutableStateOf("") }
    var sisaSlot by remember { mutableStateOf(0) }

    var pickerDate by remember { mutableStateOf(LocalDate.now()) }
    val formattedDate by remember { derivedStateOf {
        DateTimeFormatter
            .ofPattern("dd-MM-yyyy")
            .format(pickerDate)
    } }

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
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {
                Row {
                    Column(
                        modifier = modifier
                            .width(300.dp)
                    ) {
                        Text(
                            text = bengkelState.value?.namaBengkel ?: "",
                            fontSize = 18.sp,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${bengkelState.value?.lokasiBengkel}, ${bengkelState.value?.alamatBengkel}"
                        )
                    }
                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = modifier
                            .padding(top = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = if (favorit) Icons.Outlined.Bookmarks else Icons.Filled.Bookmarks,
                            contentDescription = "Favorit Bengkel",
                            modifier = modifier
                                .clickable {
                                    viewModel.togleFavorit(userModel.id, bengkelId)
                                    favorit = !favorit
                                }
                        )
                    }
                }
                Text(
                    text = bengkelState.value?.numberBengkel ?: ""
                )
                Row(
                    modifier = modifier
                        .clickable {
                            openGmaps(context, bengkelState.value?.gmapsBengkel ?: "")
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "GMAPS BENGKEL"
                    )
                    Text(
                        text = "Lokasi Bengkel Pada Gmaps"
                    )
                }
                ExposedDropdownMenuBox(
                    expanded = isExpendedKendaraan,
                    onExpandedChange = { isExpendedKendaraan = !isExpendedKendaraan }
                ) {
                    TextField(
                        value = selectedTextKendaraan,
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
                        bengkelState.value?.jenisKendaraan?.forEach { option ->
                            DropdownMenuItem(
                                text = { option?.let { Text(it) } },
                                onClick = {
                                    option?.let { selectedTextKendaraan = it }
                                    isExpendedKendaraan = false
                                },
                                modifier = modifier
                                    .background(Color.White)
                            )
                        }
                    }
                }
                ExposedDropdownMenuBox(
                    expanded = isExpendedKendaraanUser,
                    onExpandedChange = { isExpendedKendaraanUser = !isExpendedKendaraanUser }
                ) {
                    val filteredKendaraanUser = kendaraanState?.let { kendaraanList ->
                        kendaraanList.value?.filter { kendaraan ->
                            kendaraan?.jenisKendaraan.equals(selectedTextKendaraan, ignoreCase = true)
                        }
                    }
                    TextField(
                        value = selectedTextKendaraanUser,
                        onValueChange = {},
                        readOnly = true,
                        shape = RoundedCornerShape(10.dp),
                        label = { Text("Pilih Kendaraan Kamu") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.outline,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            containerColor = Color.White,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpendedKendaraanUser)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )

                    ExposedDropdownMenu(
                        expanded = isExpendedKendaraanUser,
                        onDismissRequest = { isExpendedKendaraanUser = false },
                        modifier = Modifier
                            .background(Color.White)
                    ) {
                        if (filteredKendaraanUser.isNullOrEmpty()) {
                        DropdownMenuItem(
                            text = { Text("Kamu tidak memiliki data kendaraan silakan mengisi data kendaraan") },
                            onClick = {
                                isExpendedKendaraanUser = false
                            },
                            modifier = Modifier
                                .background(Color.White)
                        )
                    } else {
                        filteredKendaraanUser.forEach { option ->
                            val teksKendaraan = "${option?.merekKendaraan ?: ""} - ${option?.platKendaraan ?: ""}"
                            DropdownMenuItem(
                                text = { Text(teksKendaraan) },
                                onClick = {
                                    option?.id?.let { idSelectedKendaraanUser = it }
                                    teksKendaraan.let { selectedTextKendaraanUser = it }
                                    isExpendedKendaraanUser = false
                                },
                                modifier = Modifier
                                    .background(Color.White)
                            )
                        }
                    }
                    }
                }
                ExposedDropdownMenuBox(
                    expanded = isExpendedLayanan,
                    onExpandedChange = { isExpendedLayanan = !isExpendedLayanan }
                ) {
                    TextField(
                        value = selectedTextLayanan,
                        onValueChange = {},
                        readOnly = true,
                        shape = RoundedCornerShape(10.dp),
                        label = { Text("Jenis Layanan") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.outline,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            containerColor = Color.White,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpendedLayanan)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = isExpendedLayanan,
                        onDismissRequest = { isExpendedLayanan = false },
                        modifier = modifier
                            .background(Color.White)
                    ) {
                        jenisLayananState.value?.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(text = "${option?.namaLayanan} - ${option?.hargaLayanan}") },
                                onClick = {
                                    selectedTextLayanan = option?.namaLayanan ?: ""
                                    isExpendedLayanan = false
                                    jenisLayanan = option?.jenisLayanan?.joinToString(separator = ", ") ?: ""
                                    hargaLayanan = option?.hargaLayanan ?: 0
                                },
                                modifier = modifier
                                    .background(Color.White)
                            )
                        }
                    }
                }
                Text(
                    text = "Jenis Perbaikan: $jenisLayanan"
                )
                Text(
                    text = "Harga Layanan: ${hargaLayanan.toRupiahString()}",
                    modifier = modifier
                        .padding(bottom = 16.dp)
                )

                val dateDialogState = rememberMaterialDialogState()
                var isDateDialogOpen by remember { mutableStateOf(false) }
                OutlinedTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .clickable {
                            dateDialogState.show()
                            isDateDialogOpen = true
                        }
                    ,
                    label = {
                        Text(
                            text = "Pilih Tanggal",
                            color = Color.Black
                        )
                    },
                    value = if (!isDateDialogOpen) "" else "Selected Date: $formattedDate",
                    onValueChange = {},
                    readOnly = true,
                    singleLine = true,
                    enabled = false,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        disabledBorderColor = Color.Black,
                        disabledTextColor = Color.Black,
                        containerColor = Color.White,
                    ),
                    shape = RoundedCornerShape(10.dp),
                )

                MaterialDialog(
                    dialogState = dateDialogState,
                    buttons = {
                        positiveButton("Ok"){
                            val hariOperasional = bengkelState.value?.hariOperasional ?: emptyList()
                            val selectedDate = pickerDate
                            val dayOfWeek = selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("id")).toLowerCase(Locale.getDefault())
                            val currentDate = LocalDate.now()

                            if (dayOfWeek in hariOperasional.map { it?.toLowerCase(Locale.getDefault()) ?: "" } &&
                                !selectedDate.isBefore(currentDate)) {
                                selectedHariOperasional = dayOfWeek
                                pickerDate = selectedDate
                            } else {
                                Toast.makeText(context, "Bengkel tidak buka pada tanggal ini atau tanggal sudah lewat", Toast.LENGTH_SHORT).show()
                                dateDialogState.show()
                            }
                            isDateDialogOpen = true
                        }
                        negativeButton("Cancel"){
                            isDateDialogOpen = false
                        }
                    }
                ){
                    datepicker(
                        initialDate = LocalDate.now(),
                        title = "Pilih Tanggal Reservasi",
                        allowedDateValidator = { date ->
                            val startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY)
                            val endOfWeek = startOfWeek.plusDays(6)
                            val hariOperasional = bengkelState.value?.hariOperasional ?: emptyList()
                            val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("id")).toLowerCase(Locale.getDefault())
                            dayOfWeek in hariOperasional.map { it?.toLowerCase(Locale.getDefault()) }
                            date in startOfWeek..endOfWeek
                        }
                    ){
                        pickerDate = it
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = isExpendedJamOperasional,
                    onExpandedChange = { isExpendedJamOperasional = !isExpendedJamOperasional },
                ) {
                    TextField(
                        value = selectedTextJamOperasional,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Jam Operasional") },
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpendedJamOperasional)
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.outline,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            containerColor = Color.White,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        ),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .clickable {
                                isExpendedJamOperasional = true
                            }
                    )
                    ExposedDropdownMenu(
                        expanded = isExpendedJamOperasional,
                        onDismissRequest = { isExpendedJamOperasional = false },
                        modifier = modifier
                            .background(Color.White)
                    ) {
                        jamOperasionalState.value?.filter { it?.hariOperasional?.equals(selectedHariOperasional, ignoreCase = true) ?: false }?.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(option?.jamOperasional ?: "")
                                },
                                onClick = {
                                    val jamOperasionalText = option?.jamOperasional ?: ""
                                    val jamOperasionalParts = jamOperasionalText.split(" - ")

                                    if (jamOperasionalParts.size == 2) {
                                        val startTime = jamOperasionalParts[0]
                                        val selectedTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH.mm"))
                                        val currentDate = LocalDate.now()
                                        val currentTime = LocalTime.now()

                                        if (pickerDate == currentDate && selectedTime.isBefore(currentTime)) {
                                            Toast.makeText(context, "Anda tidak dapat memilih jam yang telah dilewati", Toast.LENGTH_SHORT).show()
                                            selectedTextJamOperasional = ""
                                            sisaSlot = 0
                                        } else {
                                            selectedTextJamOperasional = jamOperasionalText
                                            isExpendedJamOperasional = false
                                            viewModel.getDetailBengkel(userId, bengkelId, formattedDate.toString(), selectedTextJamOperasional.toString())
                                            Handler().postDelayed({
                                                sisaSlot = slotState.value?.toInt() ?: 0
                                            }, 500)
                                        }
                                    }
                                },
                                modifier = modifier
                                    .background(Color.White)
                            )
                        }
                    }
                }

                Text(
                    text = "Sisa Slot: $sisaSlot",
                    modifier = modifier
                        .padding(bottom = 4.dp)
                )

                OutlinedTextField(
                    value = kendala,
                    onValueChange = { kendala = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
                    ),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Masukan Rincian Kendala",
                            color = Color(0xFF86888D)
                        )
                    },
                    label = {
                        Text(
                            text = "Masukan Rincian Kendala",
                            color = Color(0xFF86888D)
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.outline,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        containerColor = Color.White,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Button(
                    onClick = {
                        if(sisaSlot == 0){
                            Toast.makeText(context, "Slot tidak tersedia", Toast.LENGTH_SHORT).show()
                        }else if(selectedTextKendaraan.isBlank() ||
                            selectedTextKendaraanUser.isBlank() ||
                            selectedTextLayanan.isBlank() ||
                            selectedTextJamOperasional.isBlank()){
                            Toast.makeText(context, "Silahkan isi data yang diperlukan", Toast.LENGTH_SHORT).show()
                        } else{
                            viewModel.reservasiBengkel(
                                formattedDate.toString(),
                                selectedTextJamOperasional.toString(),
                                selectedTextLayanan,
                                kendala.toString(),
                                selectedTextKendaraan.toString(),
                                bengkelId,
                                userModel.id,
                                idSelectedKendaraanUser)
                            navigator.navigate(KonfirmasiScreenDestination(
                                bengkelState.value?.namaBengkel ?: "",
                                formattedDate.toString(),
                                selectedTextJamOperasional,
                            ))
                            Toast.makeText(context, "Berhasil Melakukan Reservasi", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    shape = RoundedCornerShape(10.dp),
                    modifier = modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                ){
                    Text(
                        text = "Reservasi"
                    )
                }
            }
        }
    }
}

fun openGmaps(context: Context, link: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    context.startActivity(intent)
}

fun Int.toRupiahString(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
    val formatted = formatter.format(this)
    return if (formatted.contains(",")) {
        formatted.substring(0, formatted.indexOf(","))
    } else {
        formatted
    }
}