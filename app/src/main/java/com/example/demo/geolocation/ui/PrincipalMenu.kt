package com.example.demo.geolocation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.demo.geolocation.navigation.AppScreens
import com.example.demo.geolocation.ui.components.MenuButton

@Composable
fun PrincipalMenu(navController: NavHostController) {
    Scaffold {
        val paddingValues = it

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Menu Principal", modifier = Modifier.padding(bottom = 16.dp))

            MenuButton(label = "Obtener GeoLocalizacion") { navController.navigate(AppScreens.GetLocationScreen.route) }
            MenuButton(label = "Evaluar Rango de distancia") { navController.navigate(AppScreens.GetDistanceScreen.route) }
        }
    }
}