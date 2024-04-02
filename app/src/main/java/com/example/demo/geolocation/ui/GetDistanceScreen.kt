package com.example.demo.geolocation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.demo.geolocation.ui.components.MenuButton
import com.example.demo.geolocation.ui.components.userInRange
import com.google.android.gms.location.LocationServices

@Composable
fun GetDistanceScreen(navController: NavHostController) {
    val context = LocalContext.current
    var geoLocation = remember { mutableStateOf("Location not available") }
    var actualAddress = remember { mutableStateOf("") }
    var actualLatitude = remember { mutableStateOf(0.0) }
    var actualLongitude = remember { mutableStateOf(0.0) }

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

        if(actualLatitude.value != 0.0 || actualLongitude.value!= 0.0) {
            ObtenerDireccion(latitud = actualLatitude.value, longitud = actualLongitude.value, direccion = actualAddress)
            Text(text = actualAddress.value,
                modifier = Modifier.padding(vertical = 16.dp),
                textAlign = TextAlign.Center)
        }


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
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val latitude = location.latitude
                        val longitude = location.longitude
                        geoLocation.value = "Latitud: $latitude, \n Longitud: $longitude"
                        actualLatitude.value = location.latitude
                        actualLongitude.value = location.longitude
                    } else {
                        geoLocation.value = "Ubicacion no disponible"
                    }
                }
        }) {
            Text(text = "Obtener Posicion Actual")
        }

        var rangeDescription = remember { mutableStateOf("") }
        var isInRange = remember { mutableStateOf(false) }
        MenuButton(
            label = "Comparar posicion Actual",
            onClick = {
                isInRange.value = userInRange(
                    SET_LATITUDE,
                    SET_LONGITUDE,
                    actualLatitude.value,
                    actualLongitude.value,
                    100
                )
            }
        )

        rangeDescription.value = if (isInRange.value){"Dentro del rango"} else{"Fuera del rango"}
        Text(
            text = "Direccion Establecida",
            modifier = Modifier.padding(bottom = 10.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Latitud: $SET_LATITUDE, \n Longitud: $SET_LONGITUDE",
            modifier = Modifier.padding(bottom = 10.dp),
            textAlign = TextAlign.Center
        )
        var address = remember { mutableStateOf("") }
        ObtenerDireccion(SET_LATITUDE, SET_LONGITUDE, address)
        if (address.value != ""){
            Text(text = address.value,
                modifier = Modifier.padding(top = 16.dp),
                textAlign = TextAlign.Center)
        }
        Text(
            text = rangeDescription.value,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}


const val SET_LATITUDE = -33.43651077353345//-33.382204505588284
const val SET_LONGITUDE = -70.65714453315124//-70.6403269736177,