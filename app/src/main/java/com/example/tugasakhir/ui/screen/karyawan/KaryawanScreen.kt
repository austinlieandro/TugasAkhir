package com.example.tugasakhir.ui.screen.karyawan

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.KaryawanModelFactory
import com.example.tugasakhir.ui.components.karyawan.KaryawanItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.EditKaryawanScreenDestination
import com.ramcosta.composedestinations.generated.destinations.InputKaryawanScerenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalFoundationApi::class)
@Destination<RootGraph>
@Composable
fun KarayawanScreen(
    idBengkel: Int,
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: KaryawanViewModel = viewModel(
        factory = KaryawanModelFactory.getInstance(LocalContext.current)
    )
){
    val karyawanListState = viewModel.karyawanList.observeAsState()
    val statusState by viewModel.status.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.getKaryawan(idBengkel)
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
            LazyColumn(
                contentPadding = PaddingValues(bottom = 30.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
                    .padding(horizontal = 16.dp)
            ){
                item{
                    Row(
                        modifier = modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Daftar Karyawan",
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
                                contentDescription = "Add Karyawan",
                                modifier = modifier
                                    .clickable {
                                        navigator.navigate(InputKaryawanScerenDestination(bengkelId = idBengkel, "lainnya"))
                                    }
                            )
                        }
                    }

                }
                if (statusState) {
                    items(karyawanListState.value ?: emptyList()){ data ->
                        KaryawanItem(
                            namaKaryawan = data?.namaKaryawan ?: "",
                            modifier = modifier
                                .clickable {
                                    navigator.navigate(EditKaryawanScreenDestination(data?.bengkelsId ?: 0, data?.id ?: 0, data?.namaKaryawan ?: ""))
                                }
                                .animateItemPlacement(tween(durationMillis = 100))
                        )
                    }
                }
            }
        }
    }
}