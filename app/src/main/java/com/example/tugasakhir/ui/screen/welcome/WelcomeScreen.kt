package com.example.tugasakhir.ui.screen.welcome

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.UserModelFactory
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.BengkelScreenDestination
import com.ramcosta.composedestinations.generated.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.generated.destinations.RegisterScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination<RootGraph>(start = true)
@Composable
fun WelcomeScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = viewModel(
        factory = UserModelFactory.getInstance(LocalContext.current)
    )
){
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val userSession by viewModel.getSession().observeAsState()

    BackHandler(enabled = true) {
        scope.launch {
            (context as? Activity)?.finish()
        }
    }

    userSession?.let {
        if (it.isLogin == true) {
            navigator.navigate(BengkelScreenDestination)
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Selamat datang pada aplikasi ....",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(8.dp)
            )
            Button(
                onClick = {
                    navigator.navigate(LoginScreenDestination)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Red),
                modifier = modifier
                    .size(300.dp, 50.dp)
            ) {
                Text(
                    text = "Login",
                    fontSize = 16.sp
                )
            }
            Button(
                onClick = {
                    navigator.navigate(RegisterScreenDestination)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = modifier
                    .padding(8.dp)
                    .size(300.dp, 50.dp)
                    .border(2.dp, Color.Red, RoundedCornerShape(10.dp))
            ) {
                Text(
                    text = "Register",
                    fontSize = 16.sp,
                    color = Color.Red
                )
            }
        }
    }
}