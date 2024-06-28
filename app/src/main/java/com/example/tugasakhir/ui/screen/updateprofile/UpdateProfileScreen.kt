package com.example.tugasakhir.ui.screen.updateprofile

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
fun UpdateProfileScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: UpdateProfileViewModel = viewModel(
        factory = UserModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore)
){
    val profileState = viewModel.profile.observeAsState()
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    val statusState by viewModel.status.observeAsState(false)

    val context = LocalContext.current

    LaunchedEffect(userModel.id) {
        viewModel.getProfile(userModel.id)
    }

    LaunchedEffect(profileState.value) {
        profileState.value?.let { data ->
            nama = data.name ?: ""
            email = data.email ?: ""
            username = data.username ?: ""
            phone = data.phone ?: ""
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
            ) {
                Text(
                    text = "Edit Profile",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Nama",
                            color = Color(0xFF86888D)
                        )
                    },
                    label = {
                        Text(
                            text = "Nama",
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
                    value = email,
                    onValueChange = { email = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Email",
                            color = Color(0xFF86888D)
                        )
                    },
                    label = {
                        Text(
                            text = "Email",
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
                    value = username,
                    onValueChange = { username = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Username",
                            color = Color(0xFF86888D)
                        )
                    },
                    label = {
                        Text(
                            text = "Username",
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
                    value = phone,
                    onValueChange = { phone = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Phone Number",
                            color = Color(0xFF86888D)
                        )
                    },
                    label = {
                        Text(
                            text = "Phone Number",
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
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Password",
                            color = Color(0xFF86888D)
                        )
                    },
                    label = {
                        Text(
                            text = "Password",
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
                        viewModel.updateProfile(userModel.id, nama, email, username, password, phone)
                        Toast.makeText(context, "Berhasil update data kendaraan", Toast.LENGTH_SHORT).show()
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Update Profile"
                    )
                }
            }
        }
    }
}