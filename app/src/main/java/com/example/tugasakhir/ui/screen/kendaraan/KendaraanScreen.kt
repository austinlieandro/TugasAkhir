package com.example.tugasakhir.ui.screen.kendaraan

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
import com.example.tugasakhir.data.factory.UserModelFactory
import com.example.tugasakhir.ui.components.kendaraan.KendaraanItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.DetailKendaraanScreenDestination
import com.ramcosta.composedestinations.generated.destinations.InputKendaraanScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun KendaraanScreen(
    navigator: DestinationsNavigator,
    idUser: Int,
    modifier: Modifier = Modifier,
    viewModel: KendaraanViewModel = viewModel(
        factory = UserModelFactory.getInstance(LocalContext.current)
    )
){
    val kendaraanListState = viewModel.kendaraanList.observeAsState()
    val statusState by viewModel.status.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.getKendaraan(idUser)
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
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
                    text = "List Kendaraan",
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
                        contentDescription = "Add Kendaraan",
                        modifier = modifier
                            .clickable {
                                navigator.navigate(InputKendaraanScreenDestination)
                            }
                    )
                }
            }
        }
        if (kendaraanListState.value?.isNotEmpty() == true) {
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
                            text = "List Kendaraan",
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
                                contentDescription = "Add Kendaraan",
                                modifier = modifier
                                    .clickable {
                                        navigator.navigate(InputKendaraanScreenDestination)
                                    }
                            )
                        }
                    }
                }
                if (statusState) {
                    items(kendaraanListState.value ?: emptyList()){ data ->
                        KendaraanItem(
                            jenisKendaraan = data?.jenisKendaraan ?: "",
                            merekKendaraan = data?.merekKendaraan ?: "",
                            platKendaraan = data?.platKendaraan ?: "",
                            modifier = modifier
                                .clickable {
                                    navigator.navigate(DetailKendaraanScreenDestination(data?.id ?: 0, idUser))
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
                    text = "Kamu tidak memiliki kendaraan",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}