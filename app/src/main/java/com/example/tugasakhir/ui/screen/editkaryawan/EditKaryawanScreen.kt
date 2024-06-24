package com.example.tugasakhir.ui.screen.editkaryawan

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
import com.example.tugasakhir.ui.theme.TugasAkhirTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.DasbboardScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KarayawanScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditKaryawanScreen(
    navigator: DestinationsNavigator,
    idBengkel: Int,
    idKaryawan: Int,
    namaKaryawan: String,
    modifier: Modifier = Modifier,
    viewModel: EditKaryawanViewModel = viewModel(
        factory = KaryawanModelFactory.getInstance(LocalContext.current)
    )
){
    var nama_karyawan by remember { mutableStateOf(namaKaryawan) }
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
                text = "Edit Karyawan",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = nama_karyawan,
                onValueChange = { nama_karyawan = it },
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
                    viewModel.editKaryawan(idBengkel, idKaryawan, nama_karyawan)
                    Toast.makeText(context, "Berhasil update karyawan", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Update Data Karyawan")
            }
            Button(
                onClick = {
                    viewModel.deleteKaryawan(idBengkel, idKaryawan)
                    Toast.makeText(context, "Berhasil delete karyawan", Toast.LENGTH_SHORT).show()
                    navigator.navigate(KarayawanScreenDestination(idBengkel))
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Hapus Data Karyawan")
            }
        }
    }
}