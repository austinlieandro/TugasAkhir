package com.example.tugasakhir.ui.screen.register

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.UserModelFactory
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel(
        factory = UserModelFactory.getInstance(LocalContext.current)
    )
){
    var fullname by remember { mutableStateOf("") }
    var numberHandphone by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var passwordRegister by remember { mutableStateOf("") }
    var showPasswordRegister by remember { mutableStateOf(value = false) }

    var isValidEmpty by remember { mutableStateOf(true) }
    var isValidEmail by remember { mutableStateOf(true) }
    var isValidEmptyPassword by remember { mutableStateOf(true) }
    var isValidPassword by remember { mutableStateOf(true) }

    val localFocusManager = LocalFocusManager.current

    val context = LocalContext.current

    val statusState by viewModel.status.observeAsState(false)
    val errorState by viewModel.error.observeAsState(null)

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Mendaftar Akun",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Mohon masukan data anda!",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
            )
            OutlinedTextField(
                value = fullname,
                onValueChange = { fullname = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
                ),
                singleLine = true,
                placeholder = {
                    Text(
                        text = "Nama Lengkap",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "Nama Lengkap",
                        color = Color(0xFF86888D)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorScheme.outline,
                    unfocusedBorderColor = colorScheme.outline,
                    containerColor = Color.White,
                    focusedTextColor = colorScheme.onSurface,
                    unfocusedTextColor = colorScheme.onSurface,
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(327.dp)
            )
            Box(
                modifier = Modifier
                    .width(327.dp)
                    .padding(8.dp, 0.dp)
            ){
                Text(
                    text = "",
                    fontSize = 12.sp
                )
            }
            OutlinedTextField(
                value = numberHandphone,
                onValueChange = { numberHandphone = it },
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
                        text = "No Handphone",
                        color = Color(0xFF86888D)
                    )
                },
                label = {
                    Text(
                        text = "No Handphone",
                        color = Color(0xFF86888D)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorScheme.outline,
                    unfocusedBorderColor = colorScheme.outline,
                    containerColor = Color.White,
                    focusedTextColor = colorScheme.onSurface,
                    unfocusedTextColor = colorScheme.onSurface,
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(327.dp)
            )
            Box(
                modifier = Modifier
                    .width(327.dp)
                    .padding(8.dp, 0.dp)
            ){
                Text(
                    text = "",
                    fontSize = 12.sp
                )
            }
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
                ),
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
                    focusedBorderColor = colorScheme.outline,
                    unfocusedBorderColor = colorScheme.outline,
                    containerColor = Color.White,
                    focusedTextColor = colorScheme.onSurface,
                    unfocusedTextColor = colorScheme.onSurface,
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(327.dp)
            )
            Box(
                modifier = Modifier
                    .width(327.dp)
                    .padding(8.dp, 0.dp)
            ){
                Text(
                    text = "",
                    fontSize = 12.sp
                )
            }
            OutlinedTextField(
                value = email,
                onValueChange = {input ->
                    email = input
                    isValidEmpty = input.isNotEmpty()
                    isValidEmail = isValidEmail(input)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.moveFocus(FocusDirection.Down) }
                ),
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
                    focusedBorderColor = colorScheme.outline,
                    unfocusedBorderColor = colorScheme.outline,
                    containerColor = Color.White,
                    focusedTextColor = colorScheme.onSurface,
                    unfocusedTextColor = colorScheme.onSurface,
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(327.dp),
            )
            Box(
                modifier = Modifier
                    .width(327.dp)
                    .padding(8.dp, 0.dp)
            ){
                when {
                    !isValidEmpty ->  Text(
                        text = "Tolong masukan email",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                    !isValidEmail ->  Text(
                        text = "Tolong masukan email yang valid",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                    else -> Text(
                        text = "",
                        fontSize = 12.sp
                    )
                }
            }
            OutlinedTextField(
                value = passwordRegister,
                onValueChange = { input ->
                    passwordRegister = input
                    isValidEmptyPassword = input.isNotEmpty()
                    isValidPassword = isValidPassword(input) },
                visualTransformation = if (showPasswordRegister) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.clearFocus() }
                ),
                singleLine = true,
                trailingIcon = {
                    if(showPasswordRegister){
                        IconButton(onClick = { showPasswordRegister = false }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "Hide Password",
                                tint = colorScheme.onSurface
                            )
                        }
                    } else{
                        IconButton(onClick = { showPasswordRegister = true }) {
                            Icon(
                                imageVector = Icons.Filled.VisibilityOff,
                                contentDescription = "Hide Password",
                                tint = colorScheme.onSurface
                            )
                        }
                    }
                },
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
                    focusedBorderColor = colorScheme.outline,
                    unfocusedBorderColor = colorScheme.outline,
                    containerColor = Color.White,
                    focusedTextColor = colorScheme.onSurface,
                    unfocusedTextColor = colorScheme.onSurface,
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(327.dp)
            )
            Box(
                modifier = Modifier
                    .width(327.dp)
                    .padding(8.dp, 0.dp)
            ) {
                when {
                    !isValidEmpty ->  Text(
                        text = "Mohon masukan password",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                    !isValidEmail ->  Text(
                        text = "Password minimal 8 karekter",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                    else -> Text(
                        text = "",
                        fontSize = 12.sp
                    )
                }
            }
            Button(
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                onClick = {
                    if(!isValidEmail || !isValidPassword){
                        Toast.makeText(context, "Masukan Format email dan password yang benar", Toast.LENGTH_SHORT).show()
                    }else if(fullname.length == 0 || email.length == 0 || passwordRegister.length == 0 || numberHandphone.length == 0){
                        Toast.makeText(context, "Harap isi semua data terlebih dahulu", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        viewModel.register(fullname, email, username, passwordRegister, numberHandphone)
                    }
                },
                modifier = Modifier
                    .size(327.dp, 55.dp)
                    .padding(0.dp, 8.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = "Daftarkan Akun",
                )
            }
            LaunchedEffect(statusState) {
                if (statusState) {
                    navigator.navigate(LoginScreenDestination)
                }
            }
            errorState?.let { errorMsg ->
                if (errorMsg != null) {
                    Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                }
            }
        }
        Box(
            contentAlignment = Alignment.Center,
        ){
            if (viewModel.loading.value == true){
                CircularProgressIndicator()
            }
        }
    }
}

fun isValidEmail(text: String): Boolean{
    return Patterns.EMAIL_ADDRESS.matcher(text).matches()
}

fun isValidPassword(text: String): Boolean{
    return text.matches(Regex(".{8,}"))
}