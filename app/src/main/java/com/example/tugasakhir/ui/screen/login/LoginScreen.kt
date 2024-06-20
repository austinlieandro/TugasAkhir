package com.example.tugasakhir.ui.screen.login

import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tugasakhir.data.factory.UserModelFactory
import com.example.tugasakhir.ui.navigation.Screen
import com.example.tugasakhir.ui.theme.TugasAkhirTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(
        factory = UserModelFactory.getInstance(LocalContext.current)
    )
){
    var username by remember { mutableStateOf("") }
    var passwordLogin by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(value = false) }

    val localFocusManager = LocalFocusManager.current

    val context = LocalContext.current

    val statusState by viewModel.status.observeAsState(false)
    val errorState by viewModel.error.observeAsState(null)

    BackHandler(enabled = true) {
        navController.navigate(Screen.Welcome.route)
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Login To Your Account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Harap masukan data kamu!",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
            )
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
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    containerColor = Color.White,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(327.dp)
            )
            OutlinedTextField(
                value = passwordLogin,
                onValueChange = { passwordLogin = it },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.clearFocus() }
                ),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (showPassword) "Hide Password" else "Show Password",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
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
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    containerColor = Color.White,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(327.dp)
                    .padding(0.dp, 8.dp)
            )
            Button(
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                onClick = {
                    viewModel.login(username, passwordLogin)
                },
                modifier = Modifier
                    .size(327.dp, 55.dp)
                    .padding(0.dp, 8.dp, 0.dp, 0.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 14.sp,
                )
            }
            LaunchedEffect(statusState) {
                if (statusState) {
                    navController.navigate(Screen.Home.route)
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

@Preview
@Composable
fun PreviewLogin(){
    TugasAkhirTheme {
        LoginScreen()
    }
}