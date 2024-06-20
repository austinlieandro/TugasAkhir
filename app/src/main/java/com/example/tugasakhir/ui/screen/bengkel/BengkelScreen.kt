package com.example.tugasakhir.ui.screen.bengkel

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tugasakhir.api.response.BengkelItem
import com.example.tugasakhir.data.factory.BengkelModelFactory
import com.example.tugasakhir.ui.components.bengkel.BengkelItem

@Composable
fun BengkelScreen(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
    viewModel: BengkelViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    ),
//    navigateToDetailBengkel: (Int) -> Unit,
){
    val bengkelListState = viewModel.bengkelList.observeAsState()
    val statusState by viewModel.status.observeAsState(false)

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getBengkel()
    }
    
    Surface(
        modifier = modifier
            .fillMaxSize(),
    ){
        LazyColumn(
            contentPadding = PaddingValues(bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    text = "List Bengkel",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .padding(8.dp)
                )
            }
            if (statusState) {
                items(bengkelListState.value ?: emptyList()) { data ->
                    BengkelItem(
                        namaBengkel = data?.namaBengkel ?: "",
                        lokasiBengkel = data?.lokasiBengkel ?: "",
                        alamatBengkel = data?.alamatBengkel ?: "",
                        numberBengkel = data?.numberBengkel ?: "",
                    )
                }
            }
        }
    }
}