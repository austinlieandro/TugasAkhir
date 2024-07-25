package com.example.tugasakhir.ui.screen.bengkel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.data.factory.BengkelModelFactory
import com.example.tugasakhir.data.pref.UserModel
import com.example.tugasakhir.data.pref.UserPreference
import com.example.tugasakhir.data.pref.dataStore
import com.example.tugasakhir.ui.components.bengkel.BengkelItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.BengkelDetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BengkelScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: BengkelViewModel = viewModel(
        factory = BengkelModelFactory.getInstance(LocalContext.current)
    ),
    userPreference: UserPreference = UserPreference.getInstance(LocalContext.current.dataStore)
){
    val bengkelListState = viewModel.bengkelList.observeAsState()
    val query by viewModel.query
    val statusState by viewModel.status.observeAsState(false)
    val userModel by userPreference.getSession().collectAsState(initial = UserModel("", false, 0, ""))
    val context = LocalContext.current

    var selectedFilter by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getBengkel()
    }

    Surface(
        modifier = modifier
            .fillMaxSize(),
    ){
        if (!statusState){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }else{
            Column {
                Row{
                    Text(
                        text = "Daftar Bengkel",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier
                            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                            .width(300.dp)
                    )
                    Box(
                        modifier = modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        DropDownMenu(onFilterSelected = {filter ->
                            selectedFilter = filter
                            viewModel.setJenisKendaraan(filter)
                        })
                    }

                }
                SearchBar(
                    query = query,
                    onQueryChange = viewModel::search,
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
                    shape = RoundedCornerShape(10.dp),
                    colors = SearchBarDefaults.colors(containerColor = Color.White),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint = colorScheme.onSurface
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Cari Bengkel",
                            fontSize = 14.sp,
                        )
                    },
                    modifier = modifier
                        .padding(16.dp, 0.dp, 0.dp, 32.dp)
                        .fillMaxWidth()
                        .height(55.dp)
                ) {
                }
                LazyColumn(
                    contentPadding = PaddingValues(bottom = 30.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                ) {
                    item {

                    }
                    if (statusState) {
                        items(bengkelListState.value ?: emptyList()) { data ->
                            BengkelItem(
                                namaBengkel = data?.namaBengkel ?: "",
                                lokasiBengkel = data?.lokasiBengkel ?: "",
                                alamatBengkel = data?.alamatBengkel ?: "",
                                numberBengkel = data?.numberBengkel ?: "",
                                hariBuka = data?.hariOperasional?.joinToString(separator = ", ") ?: "",
                                modifier = modifier
                                    .clickable {
                                        data?.id?.let {
                                            navigator.navigate(
                                                BengkelDetailScreenDestination(
                                                    userModel.id,
                                                    bengkelId = it
                                                )
                                            )
                                        }
                                    }
                                    .animateItemPlacement(tween(durationMillis = 100))
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropDownMenu(
    modifier: Modifier = Modifier,
    onFilterSelected: (String) -> Unit
){
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        IconButton(
            onClick = { expanded = !expanded }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Filter"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = "Mobil") },
                onClick = {
                    onFilterSelected("mobil")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(text = "Motor") },
                onClick = {
                    onFilterSelected("motor")
                    expanded = false
                }
            )
        }
    }
}

