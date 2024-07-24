package com.example.tugasakhir.ui.screen.profile

import android.os.Handler
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.UserModelFactory
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.DaftarBengkelScreenDestination
import com.ramcosta.composedestinations.generated.destinations.DasbboardScreenDestination
import com.ramcosta.composedestinations.generated.destinations.FavoritScreenDestination
import com.ramcosta.composedestinations.generated.destinations.JamOperasionalScreenDestination.invoke
import com.ramcosta.composedestinations.generated.destinations.JenisServiceScreenDestination
import com.ramcosta.composedestinations.generated.destinations.KendaraanScreenDestination
import com.ramcosta.composedestinations.generated.destinations.MerekKendaraanScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UpdateProfileScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(
        factory = UserModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore)
){
    val profileState = viewModel.profile.observeAsState()
    val statusState by viewModel.status.observeAsState(false)
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))

    LaunchedEffect(userModel.id) {
        viewModel.getProfile(userModel.id)
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
            Column {
                Text(
                    text = profileState.value?.name ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = modifier
                        .padding(start = 16.dp, top = 8.dp)
                )
                Text(
                    text = profileState.value?.phone ?: "",
                    fontSize = 18.sp,
                    modifier = modifier
                        .padding(start = 16.dp)
                )
                Text(
                    text = profileState.value?.userBengkel ?: "",
                    fontSize = 18.sp,
                    modifier = modifier
                        .padding(start = 16.dp, bottom = 16.dp)
                )
                Divider(
                    color = Color.Black
                )
                Row(
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .fillMaxWidth()
                        .clickable {
                            navigator.navigate(UpdateProfileScreenDestination)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Icon Edit Profile",
                        tint = colorScheme.onSurface,
                        modifier = modifier
                            .padding(0.dp, 10.dp)
                    )
                    Text(
                        text = "Edit Profile",
                        color = colorScheme.onSurface,
                        modifier = modifier
                            .padding(8.dp, 0.dp, 8.dp, 0.dp)
                    )
                    Box(
                        modifier = modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            imageVector = Icons.Filled.NavigateNext,
                            contentDescription = "Navigate to Edit Profile",
                            tint = colorScheme.onSurface,
                        )
                    }
                }
                Row(
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp, top = 12.dp)
                        .fillMaxWidth()
                        .clickable {
                            navigator.navigate(KendaraanScreenDestination(idUser = userModel.id))
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.DirectionsCar,
                        contentDescription = "Icon Kendaraan",
                        tint = colorScheme.onSurface,
                        modifier = modifier
                            .padding(0.dp, 10.dp)
                    )
                    Text(
                        text = "Daftar Kendaraan",
                        color = colorScheme.onSurface,
                        modifier = modifier
                            .padding(8.dp, 0.dp, 8.dp, 0.dp)
                    )
                    Box(
                        modifier = modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            imageVector = Icons.Filled.NavigateNext,
                            contentDescription = "Navigate to List Kendaraan",
                            tint = colorScheme.onSurface,
                        )
                    }
                }
                Row(
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp, top = 12.dp)
                        .fillMaxWidth()
                        .clickable {
                            if (profileState.value?.userBengkel?.toLowerCase() == "pelanggan") {
                                navigator.navigate(DaftarBengkelScreenDestination)
                            } else if(profileState.value?.userBengkel?.toLowerCase() == "admin"){
                                navigator.navigate(MerekKendaraanScreenDestination)
                            }
                            else {
                                navigator.navigate(DasbboardScreenDestination(idBengkel = userModel.bengkels_id))
                            }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.ManageAccounts,
                        contentDescription = "Icon Daftar Bengkel",
                        tint = colorScheme.onSurface,
                        modifier = modifier
                            .padding(0.dp, 10.dp)
                    )
                    Text(
                        text = if (userModel.user_bengkel.lowercase() == "pelanggan"){
                            "Daftar pemilik Bengkel"
                        }else if(profileState.value?.userBengkel?.toLowerCase() == "admin"){
                            "Nambah Merek Kendaraan"
                        }
                        else {
                            "Dasbor bengkel"
                        },
                        color = colorScheme.onSurface,
                        modifier = modifier
                            .padding(8.dp, 0.dp, 8.dp, 0.dp)
                    )
                    Box(
                        modifier = modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            imageVector = Icons.Filled.NavigateNext,
                            contentDescription = "Navigate to Daftar Bengkel",
                            tint = colorScheme.onSurface,
                        )
                    }
                }
                Row(
                    modifier = modifier
                        .padding(start = 16.dp, end = 16.dp, top = 12.dp)
                        .fillMaxWidth()
                        .clickable {
                            navigator.navigate(FavoritScreenDestination)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Bookmarks,
                        contentDescription = "Icon Favorit Bengkel",
                        tint = colorScheme.onSurface,
                        modifier = modifier
                            .padding(0.dp, 10.dp)
                    )
                    Text(
                        text = "Favorit Bengkel",
                        color = colorScheme.onSurface,
                        modifier = modifier
                            .padding(8.dp, 0.dp, 8.dp, 0.dp)
                    )
                    Box(
                        modifier = modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            imageVector = Icons.Filled.NavigateNext,
                            contentDescription = "Navigate to Daftar Bengkel",
                            tint = colorScheme.onSurface,
                        )
                    }
                }
                if (profileState.value?.userBengkel == "admin"){
                    Row(
                        modifier = modifier
                            .padding(start = 16.dp, end = 16.dp, top = 12.dp)
                            .fillMaxWidth()
                            .clickable {
                                navigator.navigate(JenisServiceScreenDestination)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Icon Favorit Bengkel",
                            tint = colorScheme.onSurface,
                            modifier = modifier
                                .padding(0.dp, 10.dp)
                        )
                        Text(
                            text = "Tambah/Edit Jenis Servis",
                            color = colorScheme.onSurface,
                            modifier = modifier
                                .padding(8.dp, 0.dp, 8.dp, 0.dp)
                        )
                        Box(
                            modifier = modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Icon(
                                imageVector = Icons.Filled.NavigateNext,
                                contentDescription = "Navigate to Jenis Service",
                                tint = colorScheme.onSurface,
                            )
                        }
                    }
                }
                Box(
                    modifier = modifier
                        .padding(20.dp, 25.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        onClick = {
                            viewModel.logout()
                            Handler().postDelayed({
                                navigator.navigate(WelcomeScreenDestination)
                            }, 500)
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .border(2.dp, color = Color.Red, RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = "Logout",
                            color = Color.Red
                        )
                    }
                }
            }
        }
    }
}