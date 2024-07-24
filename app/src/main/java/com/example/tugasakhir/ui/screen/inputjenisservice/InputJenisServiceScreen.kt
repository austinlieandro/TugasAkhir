package com.example.tugasakhir.ui.screen.inputjenisservice

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.KaryawanModelFactory
import com.example.tugasakhir.data.factory.UserModelFactory
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.example.tugasakhir.ui.screen.inputkaryawan.InputKaryawanViewModel
import com.example.tugasakhir.ui.theme.TugasAkhirTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputJenisServiceScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: InputJenisServiceViewModel = viewModel(
        factory = UserModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore)
){
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))
    var namaService by remember { mutableStateOf("") }
    val context = LocalContext.current

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Memasukan Jenis Servis",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = namaService,
                onValueChange = { namaService = it },
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Nama Servis",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Nama Servis",
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
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            )
            Button(
                onClick = {
                    if (namaService.isBlank()){
                        Toast.makeText(context, "Nama servis tidak boleh kosong", Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.inputJenisService(namaService, userModel.id)
                        Toast.makeText(context, "Berhasil menambahkan jenis servis", Toast.LENGTH_SHORT).show()
                        namaService = ""
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Simpan Jenis Servis")
            }
            Button(
                onClick = {
                    navigator.popBackStack()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Selesai Menyimpan Data Servis")
            }
        }
    }
}