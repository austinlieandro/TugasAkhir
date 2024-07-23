package com.example.tugasakhir.ui.screen.jenislayanan

import androidx.activity.compose.BackHandler
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
import com.example.tugasakhir.ui.components.jenislayanan.JenisLayananItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.BengkelScreenDestination
import com.ramcosta.composedestinations.generated.destinations.InputJenisLayananScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UpdateJenisLayananScreenDestination
import com.ramcosta.composedestinations.generated.destinations.WelcomeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun JenisLayananScreen(
    navigator: DestinationsNavigator,
    jenis: String,
    bengkelId: Int,
    modifier: Modifier = Modifier,
    viewModel: JenisLayananViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    )
){
    val statusState by viewModel.status.observeAsState(false)
    val jenisLayananState by viewModel.jenisLayananList.observeAsState()

    if (jenis == "daftar") {
        BackHandler(enabled = true) {
            navigator.navigate(BengkelScreenDestination)
        }
    }else{
        BackHandler(enabled = true) {
            navigator.popBackStack()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.getJenisLayanan(bengkelId)
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
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Daftar Jenis Layanan",
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
                            contentDescription = "Add Jenis Layanan",
                            modifier = modifier
                                .clickable {
                                    navigator.navigate(InputJenisLayananScreenDestination("lainnya",bengkelId))
                                }
                        )
                    }
                }
            }
            if (jenisLayananState?.isNotEmpty() == true) {
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                ){
                    item {
                        Row(
                            modifier = modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Daftar Jenis Layanan",
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
                                    contentDescription = "Add Jenis Layanan",
                                    modifier = modifier
                                        .clickable {
                                            navigator.navigate(InputJenisLayananScreenDestination("lainnya",bengkelId))
                                        }
                                )
                            }
                        }
                    }
                    if (statusState) {
                        items(jenisLayananState?: emptyList()){ data ->
                            JenisLayananItem(
                                namaLayanan = data?.namaLayanan ?: "",
                                jenisLayanan = data?.jenisLayanan ?: emptyList(),
                                hargaLayanan = data?.hargaLayanan ?: 0,
                                modifier = modifier
                                    .clickable {
                                        navigator.navigate(UpdateJenisLayananScreenDestination(bengkelId, data?.id ?: 0))
                                    }
                            )
                        }
                    }
                }
            }else{
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = "Kamu tidak memiliki Jenis Layanan",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}