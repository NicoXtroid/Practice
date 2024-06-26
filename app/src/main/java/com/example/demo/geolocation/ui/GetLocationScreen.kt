package com.example.demo.geolocation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.demo.MainActivity
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

@SuppressLint("MissingPermission")
@Composable
fun GetLocationScreen(navController: NavHostController) {
    var geoLocation = remember { mutableStateOf("Location not available") }
    var address = remember { mutableStateOf("") }
    var actualLatitude = remember { mutableStateOf(0.0) }
    var actualLongitude = remember { mutableStateOf(0.0) }

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = geoLocation.value,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Button(onClick = {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as MainActivity,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    101
                )
                return@Button
            }

            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: android.location.Location? ->
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        geoLocation.value = "Latitud: $latitude, \n Longitud: $longitude"
                        actualLatitude.value = latitude
                        actualLongitude.value = longitude
                    } else {
                        geoLocation.value = "Location not available"
                    }
                }

        }) {
            Text(text = "Obtener Ubicacion")
        }
        ObtenerDireccion(actualLatitude.value, actualLongitude.value, address)
        if (address.value != ""){
            Text(text = address.value,
                modifier = Modifier.padding(top = 16.dp),
                textAlign = TextAlign.Center)
        }

    }
}


@Composable
fun ObtenerDireccion(latitud: Double, longitud: Double, direccion: MutableState<String>) {
    val context = LocalContext.current

    LaunchedEffect(latitud, longitud) {
        val geocoder = Geocoder(context, Locale.getDefault())

        try {
            val direcciones = withContext(Dispatchers.IO) {
                geocoder.getFromLocation(latitud, longitud, 1)
            }

            if (direcciones != null) {
                if (direcciones.isNotEmpty()) {
                    val direccionCompleta = direcciones[0].getAddressLine(0)
                    direccion.value = direccionCompleta ?: "Dirección desconocida"
                } else {
                    direccion.value = "No se encontró ninguna dirección para estas coordenadas."
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            direccion.value = "Error al obtener la dirección."
        }
    }

}
