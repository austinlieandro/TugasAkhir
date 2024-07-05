package com.example.tugasakhir.ui.screen.updatejenislayanan

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.ramcosta.composedestinations.generated.destinations.JenisLayananScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateJenisLayananScreen(
    navigator: DestinationsNavigator,
    bengkelId: Int,
    idJenisLayanan: Int,
    modifier: Modifier = Modifier,
    viewModel: UpdateJenisLayananViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore),
){
    val detailJenisLayanan = viewModel.jenisLayananBengkel.observeAsState()
    val statusState by viewModel.status.observeAsState(false)
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))
    LaunchedEffect(userModel.bengkels_id) {
        viewModel.getDetailJenisLayanan(idJenisLayanan)
    }

    val localFocusManager = LocalFocusManager.current
    val context = LocalContext.current

    var namaLayanan by remember { mutableStateOf("") }
    var hargaLayanan by remember { mutableStateOf("") }

    var isOliChecked by remember { mutableStateOf(false) }
    var isRemChecked by remember { mutableStateOf(false) }
    var isBusiChecked by remember { mutableStateOf(false) }
    var isAkiChecked by remember { mutableStateOf(false) }
    var isListrikChecked by remember { mutableStateOf(false) }
    var isSuspensiChecked by remember { mutableStateOf(false) }
    var isMesinChecked by remember { mutableStateOf(false) }
    var isBanChecked by remember { mutableStateOf(false) }
    var isRantaiChecked by remember { mutableStateOf(false) }
    var isKarburatorChecked by remember { mutableStateOf(false) }
    var isBodyChecked by remember { mutableStateOf(false) }
    var isFilterChecked by remember { mutableStateOf(false) }
    var isAcChecked by remember { mutableStateOf(false) }
    var isTransmisiChecked by remember { mutableStateOf(false) }
    var isRadiatorChecked by remember { mutableStateOf(false) }
    var selectedLayanan by remember { mutableStateOf(mutableListOf<String>()) }

    LaunchedEffect(detailJenisLayanan.value) {
        detailJenisLayanan.value?.let { data ->
            namaLayanan = data.namaLayanan ?: ""
            hargaLayanan = data.hargaLayanan.toString()

            val layananSet = data.jenisLayanan?.filterNotNull()?.toSet() ?: emptySet()
            isOliChecked = layananSet.contains("Oli")
            isRemChecked = layananSet.contains("Rem")
            isBusiChecked = layananSet.contains("Busi")
            isAkiChecked = layananSet.contains("Aki")
            isListrikChecked = layananSet.contains("Listrik")
            isSuspensiChecked = layananSet.contains("Suspensi")
            isMesinChecked = layananSet.contains("Mesin")
            isBanChecked = layananSet.contains("Ban")
            isRantaiChecked = layananSet.contains("Rantai")
            isKarburatorChecked = layananSet.contains("Karburator/Injektor")
            isBodyChecked = layananSet.contains("Body")
            isFilterChecked = layananSet.contains("Filter")
            isAcChecked = layananSet.contains("AC")
            isTransmisiChecked = layananSet.contains("Transmisi")
            isRadiatorChecked = layananSet.contains("Radiator")
            selectedLayanan = layananSet.toMutableList()
        }
    }

    val layananMap = mapOf(
        "Oli" to isOliChecked,
        "Rem" to isRemChecked,
        "Busi" to isBusiChecked,
        "Aki" to isAkiChecked,
        "Listrik" to isListrikChecked,
        "Suspensi" to isSuspensiChecked,
        "Mesin" to isMesinChecked,
        "Ban" to isBanChecked,
        "Rantai" to isRantaiChecked,
        "Karburator/Injektor" to isKarburatorChecked,
        "Body" to isBodyChecked,
        "Filter" to isFilterChecked,
        "AC" to isAcChecked,
        "Transmisi" to isTransmisiChecked,
        "Radiator" to isRadiatorChecked
    )

    layananMap.forEach { (layanan, isChecked) ->
        if (isChecked && !selectedLayanan.contains(layanan)) {
            selectedLayanan.add(layanan)
        } else if (!isChecked && selectedLayanan.contains(layanan)) {
            selectedLayanan.remove(layanan)
        }
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
                            listOf(
                                "Oli" to isOliChecked,
                                "Rem" to isRemChecked,
                                "Busi" to isBusiChecked,
                                "Aki" to isAkiChecked,
                                "Listrik" to isListrikChecked,
                                "Suspensi" to isSuspensiChecked,
                                "Mesin" to isMesinChecked,
                                "Ban" to isBanChecked,
                                "Rantai" to isRantaiChecked,
                                "Karburator/Injektor" to isKarburatorChecked,
                                "Body" to isBodyChecked,
                                "Filter" to isFilterChecked,
                                "AC" to isAcChecked,
                                "Transmisi" to isTransmisiChecked,
                                "Radiator" to isRadiatorChecked
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
                                                "Oli" -> isOliChecked = it
                                                "Rem" -> isRemChecked = it
                                                "Busi" -> isBusiChecked = it
                                                "Aki" -> isAkiChecked = it
                                                "Listrik" -> isListrikChecked = it
                                                "Suspensi" -> isSuspensiChecked = it
                                                "Mesin" -> isMesinChecked = it
                                                "Ban" -> isBanChecked = it
                                                "Rantai" -> isRantaiChecked = it
                                                "Karburator/Injektor" -> isKarburatorChecked = it
                                                "Body" -> isBodyChecked = it
                                                "Filter" -> isFilterChecked = it
                                                "AC" -> isAcChecked = it
                                                "Transmisi" -> isTransmisiChecked = it
                                                "Radiator" -> isRadiatorChecked = it
                                            }
                                        }
                                    )
                                    Text(text = label, fontSize = 14.sp)
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
                            Toast.makeText(context, "Field Tidak boleh kosong", Toast.LENGTH_SHORT).show()
                        }else{
                            viewModel.updateJenisLayanan(bengkelId, idJenisLayanan, namaLayanan, selectedLayanan, hargaLayanan.toInt())
                            Toast.makeText(context, "Berhasil Update data Jenis Layanan", Toast.LENGTH_SHORT).show()
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
}