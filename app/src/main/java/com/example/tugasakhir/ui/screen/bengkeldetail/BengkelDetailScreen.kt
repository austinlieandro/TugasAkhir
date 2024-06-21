package com.example.tugasakhir.ui.screen.bengkeldetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tugasakhir.api.response.Bengkel
import com.example.tugasakhir.api.response.JamOperasionalItem
import com.example.tugasakhir.data.factory.BengkelModelFactory
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BengkelDetailScreen(
    bengkelId: Int,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: BengkelDetailViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore),
) {
    val bengkelState = viewModel.detailBengkel.observeAsState()
    val jamOperasionalState = viewModel.jamOperasionalList.observeAsState()
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))

    LaunchedEffect(Unit) {
        viewModel.getDetailBengkel(bengkelId)
    }

    val context = LocalContext.current

    var selectedTextKendaraan by remember { mutableStateOf("") }
    var selectedTextLayanan by remember { mutableStateOf("") }
    var selectedTextJamOperasional by remember { mutableStateOf("") }
    var selectedHariOperasional by remember { mutableStateOf<String?>(null) }

    var isExpendedKendaraan by remember { mutableStateOf(false) }
    var isExpendedLayanan by remember { mutableStateOf(false) }
    var isExpendedJamOperasional by remember { mutableStateOf(false) }

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
        Column(
            modifier = modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Text(
                text = bengkelState?.value?.namaBengkel ?: "",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${bengkelState?.value?.lokasiBengkel}, ${bengkelState?.value?.alamatBengkel}"
            )
            Text(
                text = bengkelState?.value?.numberBengkel ?: ""
            )
            Row(
                modifier = modifier
                    .clickable {
                        openGmaps(context, bengkelState?.value?.gmapsBengkel ?: "")
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
                    onDismissRequest = { isExpendedKendaraan = false }
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
                    onDismissRequest = { isExpendedLayanan = false }
                ) {
                    bengkelState?.value?.jenisLayanan?.forEach { option ->
                        DropdownMenuItem(
                            text = { option?.let { Text(it) } },
                            onClick = {
                                option?.let { selectedTextLayanan = it }
                                isExpendedLayanan = false
                            },
                            modifier = modifier
                                .background(Color.White)
                        )
                    }
                }
            }

            val dateDialogState = rememberMaterialDialogState()

            Button(
                onClick = {
                    dateDialogState.show()
                },
                colors = ButtonDefaults.buttonColors(Color.White),
                shape = RoundedCornerShape(10.dp),
                border = ButtonDefaults.outlinedButtonBorder,
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Pilih Tanggal",
                    color = Color.Black,
                )
            }
            Text(
                text = "Selected Date: $formattedDate",
                modifier = modifier
                    .padding(top = 8.dp)
            )

            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton("Ok"){
                        val hariOperasional = bengkelState.value?.hariOperasional ?: emptyList()
                        val selectedDate = pickerDate
                        val dayOfWeek = selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("id")).toLowerCase(Locale.getDefault())

                        if (dayOfWeek in hariOperasional.map { it?.toLowerCase(Locale.getDefault()) ?: "" }) {
                            selectedHariOperasional = dayOfWeek
                            pickerDate = selectedDate
                        } else {
                            Toast.makeText(context, "Bengkel tidak buka pada tanggal ini", Toast.LENGTH_SHORT).show()
                            dateDialogState.show()
                        }
                    }
                    negativeButton("Cancel")
                }
            ){
                datepicker(
                    initialDate = LocalDate.now(),
                    title = "Pick a date",
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
                ) {
                    jamOperasionalState.value?.filter { it?.hariOperasional?.equals(selectedHariOperasional, ignoreCase = true) ?: false }?.forEach { option ->
                        DropdownMenuItem(
                            text = { option?.jamOperasional?.let { Text(it) } },
                            onClick = {
                                option?.jamOperasional?.let {
                                    selectedTextJamOperasional = it
                                    sisaSlot = option.slot ?: 0
                                }
                                isExpendedJamOperasional = false
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
                onClick = {},
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

fun openGmaps(context: Context, link: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    context.startActivity(intent)
}