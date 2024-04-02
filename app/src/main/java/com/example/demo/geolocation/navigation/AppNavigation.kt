package com.example.demo.geolocation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.demo.geolocation.ui.GetLocationScreen
import com.example.demo.geolocation.ui.PrincipalMenu

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.PrincipalMenuScreen.route
    ){
        composable(route = AppScreens.PrincipalMenuScreen.route){
            PrincipalMenu(navController = navController)
        }
        composable(route = AppScreens.GetLocationScreen.route){
            GetLocationScreen(navController = navController)
        }

    }
}