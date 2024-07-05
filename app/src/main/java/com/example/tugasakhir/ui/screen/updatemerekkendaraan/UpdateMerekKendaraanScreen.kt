package com.example.tugasakhir.ui.screen.updatemerekkendaraan

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.UserModelFactory
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMerekKendaraanScreen(
    jenisKendaraan: String,
    merekKendaraan: String,
    userId: Int,
    merekKendaraanId: Int,
    modifier: Modifier = Modifier,
    viewModel: UpdateMerekKendaraanViewModel = viewModel(
        factory = UserModelFactory.getInstance(LocalContext.current)
    )
){
    var merekKendaraan by remember { mutableStateOf(merekKendaraan) }

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
                text = "Input Merek Kendaraan",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Jenis Kendaraan: $jenisKendaraan"
            )
            OutlinedTextField(
                value = merekKendaraan,
                onValueChange = { merekKendaraan = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
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
                    if(merekKendaraan.isBlank()){
                        Toast.makeText(context, "Harap masukan semua data terlebih dahulu", Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.updateMerekKendaraan(userId, merekKendaraanId, merekKendaraan)
                        Toast.makeText(context, "Berhasil update merek Kendaraan", Toast.LENGTH_SHORT).show()
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Update Merek Kendaraan"
                )
            }
        }
    }
}