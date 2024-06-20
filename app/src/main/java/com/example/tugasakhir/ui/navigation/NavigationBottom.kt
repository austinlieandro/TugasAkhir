package com.example.tugasakhir.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tugasakhir.ui.screen.bengkel.BengkelScreen
import com.example.tugasakhir.ui.screen.profile.ProfileScreen
import com.example.tugasakhir.ui.screen.riwayat.RiwayatScreen

@Composable
fun NavigationBottom(
    navController: NavHostController,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier){
    NavHost(
        navController = navController,
        startDestination = Screen.Bengkel.route,
        modifier = modifier
            .padding(innerPadding)
    ) {
        composable(Screen.Bengkel.route){
            BengkelScreen(
                navController = navController,
            )
        }
        composable(Screen.Riwayat.route){
            RiwayatScreen(
                navController
            )
        }
        composable(Screen.Profile.route){
            ProfileScreen(
                navController
            )
        }
    }
}