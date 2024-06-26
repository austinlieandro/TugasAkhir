package com.example.tugasakhir.ui.screen.listjamoperasional

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.BengkelModelFactory
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.example.tugasakhir.ui.components.jamoperasional.JamOperasionalItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.UpdateOperasionalScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun ListOperasionalScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: ListOperasionalViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore),
){
    val jamOperasionalState = viewModel.listOperasional.observeAsState()
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))

    LaunchedEffect(userModel.id) {
        viewModel.getJamOperasional(userModel.id, userModel.bengkels_id)
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .padding(horizontal = 16.dp)
        ) {
            item {
                Text(
                    text = "List Jam Operasional",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .padding(8.dp)
                )
            }
            items(jamOperasionalState.value ?: emptyList()){ data->
                JamOperasionalItem(
                    jam = data?.jamOperasional ?: "",
                    hari = data?.hariOperasional ?: "",
                    slot = data?.slot ?: 0,
                    modifier = modifier
                        .clickable {
                            navigator.navigate(UpdateOperasionalScreenDestination(
                                userModel.bengkels_id,
                                data?.id ?: 0,
                                data?.hariOperasional ?: "",
                                data?.jamOperasional ?: "",
                                data?.slot ?: 0)
                            )
                        }
                )
            }
        }
    }
}