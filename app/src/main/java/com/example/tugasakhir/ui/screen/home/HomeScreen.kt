package com.example.tugasakhir.ui.screen.home

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tugasakhir.ui.navigation.NavigationBottom
import com.example.tugasakhir.ui.navigation.NavigationItem
import com.example.tugasakhir.ui.navigation.Screen
import com.example.tugasakhir.ui.theme.TugasAkhirTheme

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier,
){
    val logoutStatus = remember { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        if (currentRoute == Screen.Welcome.route) {
            logoutStatus.value = true
        }
    }

    Scaffold(
        bottomBar = {
            if (!logoutStatus.value &&
                currentRoute != Screen.Welcome.route &&
                currentRoute != Screen.BengkelDetail.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavigationBottom(navController, innerPadding)
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    BottomAppBar(
        containerColor = Color.White,
        modifier = modifier
            .shadow(10.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp), true)
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItem = listOf(
            NavigationItem(
                title = "Home",
                icon = Icons.Default.Home,
                screen = Screen.Bengkel
            ),
            NavigationItem(
                title = "History",
                icon = Icons.Default.History,
                screen = Screen.Riwayat,
            ),
            NavigationItem(
                title = "Profile",
                icon = Icons.Default.Person,
                screen = Screen.Profile,
            ),
        )

        navigationItem.forEach { item ->
            val isSelected = currentRoute == item.screen?.route
            val color = if (isSelected) Color.Red else Color.Black

            NavigationBarItem(
                icon = {
                    item.icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = item.title,
                            tint = color
                        )
                    }
                },
                label = {
                    item.title?.let {
                        Text(
                            text = it,
                            color = color
                        )
                    }
                },
                selected = isSelected,
                onClick = {
                    item.screen?.let {
                        navController.navigate(it.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewHome(){
    TugasAkhirTheme {
        HomeScreen()
    }
}