package com.example.tugasakhir.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tugasakhir.ui.screen.home.HomeScreen
import com.example.tugasakhir.ui.screen.login.LoginScreen
import com.example.tugasakhir.ui.screen.register.RegisterScreen
import com.example.tugasakhir.ui.screen.welcome.WelcomeScreen

@Composable
fun Navigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route){
            WelcomeScreen(navController)
        }
        composable(Screen.Login.route){
            LoginScreen(navController)
        }
        composable(Screen.Register.route){
            RegisterScreen(navController)
        }
        composable(Screen.Home.route){
            HomeScreen()
        }
    }
}