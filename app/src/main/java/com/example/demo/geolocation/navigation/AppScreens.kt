package com.example.demo.geolocation.navigation

sealed class AppScreens(val route: String) {
    object PrincipalMenuScreen: AppScreens("principal_menu_screen")
    object GetLocationScreen: AppScreens("get_location_screen")
    object GetDistanceScreen: AppScreens("get_distance_screen")
}