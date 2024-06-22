package com.example.tugasakhir.ui.screen.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tugasakhir.BottomBarDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.BengkelScreenDestination
import com.ramcosta.composedestinations.generated.destinations.ProfileScreenDestination
import com.ramcosta.composedestinations.generated.destinations.RiwayatScreenDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.startDestination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { paddingValue ->
        DestinationsNavHost(
            modifier = modifier.padding(paddingValue),
            navController = navController,
            navGraph = NavGraphs.root
        )
    }
}

@Composable
fun BottomBar(
    navController: NavController
){
    val currentDestination: DestinationSpec = navController.currentDestinationAsState().value ?: NavGraphs.root.startDestination

    val bottomBarItems = listOf(
        BengkelScreenDestination,
        RiwayatScreenDestination,
        ProfileScreenDestination
    )
    if (currentDestination in bottomBarItems){
        BottomAppBar(
            containerColor = Color.White,
        ) {
            BottomBarDestination.entries.forEach { destination ->
                NavigationBarItem(
                    selected = currentDestination == destination.direction,
                    onClick = {
                        navController.navigate(destination.direction)
                    },
                    icon = { Icon(imageVector = destination.icon, contentDescription = destination.title) },
                    label = { Text(text = destination.title) }
                )
            }
        }
    }
}