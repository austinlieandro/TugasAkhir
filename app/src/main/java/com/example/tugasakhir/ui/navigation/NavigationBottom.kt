package com.example.tugasakhir.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tugasakhir.ui.screen.bengkel.BengkelScreen
import com.example.tugasakhir.ui.screen.bengkeldetail.BengkelDetailScreen
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
                navigateToDetailBengkel = { bengkelId ->
                    navController.navigate(Screen.BengkelDetail.createRoute(bengkelId))
                }
            )
        }
        val bengkelId = "bengkelId"
        composable(
            route = Screen.BengkelDetail.route,
            arguments = listOf(navArgument(bengkelId){ type = NavType.IntType })
        ){
            val id = it.arguments?.getInt(bengkelId) ?: 0
            BengkelDetailScreen(
                navController = navController,
                bengkelId = id,
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