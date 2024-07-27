package com.example.tugasakhir.ui.screen.prioritasbengkel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.BengkelModelFactory
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.example.tugasakhir.ui.components.prioritas.Prioritasitem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.InputPrioritasSatuanScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UpdatePrioritasScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun PrioritasBengkelScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: PrioritasBengkelViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore),
){
    val priortiasState = viewModel.prioritasList.observeAsState()
    val statusState by viewModel.status.observeAsState(false)
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))

    LaunchedEffect(userModel.id) {
        viewModel.getPrioritas(userModel.bengkels_id)
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (!statusState){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }else{
            if(priortiasState.value?.isNotEmpty() == true){
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                ) {
                    item {
                        Row(
                            modifier = modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Daftar Prioritas",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = modifier
                                    .padding(8.dp)
                            )
                            Box(
                                modifier = modifier
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Jenis Service",
                                    modifier = modifier
                                        .clickable {
                                            navigator.navigate(InputPrioritasSatuanScreenDestination)
                                        }
                                )
                            }
                        }
                    }
                    if(statusState){
                        items(priortiasState.value ?: emptyList()){ data ->
                            Prioritasitem(
                                namaKendaraan = data?.jenisKendaraan ?: "",
                                jenisKerusakan = data?.jenisKerusakan ?: "",
                                modifier = modifier
                                    .clickable {
                                        navigator.navigate(UpdatePrioritasScreenDestination(
                                            data?.id ?: 0,
                                            data?.bengkelsId ?: 0,
                                            data?.bobotNilai ?: 0,
                                            data?.bobotEstimasi ?: 0,
                                            data?.bobotUrgensi ?: 0,
                                            data?.jenisKendaraan ?: "",
                                            data?.jenisKerusakan ?: ""
                                        ))
                                    }
                            )
                        }
                    }
                }
            }else{
                Row(
                    modifier = modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Daftar Prioritas",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier
                            .padding(8.dp)
                    )
                    Box(
                        modifier = modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Jenis Service",
                            modifier = modifier
                                .clickable {
                                    navigator.navigate(InputPrioritasSatuanScreenDestination)
                                }
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = "Mohon Tambahkan Prioritas",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}