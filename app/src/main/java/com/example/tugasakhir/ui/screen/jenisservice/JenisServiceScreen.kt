package com.example.tugasakhir.ui.screen.jenisservice

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.UserModelFactory
import com.example.tugasakhir.ui.components.jenisservice.JenisServiceItem
import com.example.tugasakhir.ui.theme.TugasAkhirTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.InputJenisServiceScreenDestination
import com.ramcosta.composedestinations.generated.destinations.UpdateJenisServiceScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun JenisServiceScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: JenisServiceViewModel = viewModel(
        factory = UserModelFactory.getInstance(LocalContext.current)
    ),
){
    val statusState by viewModel.status.observeAsState(false)
    val jenisSeviceListState by viewModel.jenisSeviceList.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.getJenisService()
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
            if (jenisSeviceListState?.isNotEmpty() == true){
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
                                text = "Daftar Jenis Service",
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
                                            navigator.navigate(InputJenisServiceScreenDestination)
                                        }
                                )
                            }
                        }
                    }
                    if (statusState){
                        items(jenisSeviceListState ?: emptyList()){ data ->
                            JenisServiceItem(
                                namaService = data?.namaService ?: "",
                                modifier = modifier
                                    .clickable {
                                        navigator.navigate(UpdateJenisServiceScreenDestination(data?.namaService ?: "", data?.id ?: 0))
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
                        text = "Mohon Tambahkan Jenis Service",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}