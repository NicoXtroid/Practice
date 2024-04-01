package com.example.demo.geolocation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.demo.MainActivity
import com.google.android.gms.location.LocationServices

@Composable
fun LocationScreen2() {
    Scaffold {
        val paddingValues = it
        var location by remember { mutableStateOf("Location not available") }
        val context = LocalContext.current
        val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }



        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = location, modifier = Modifier.padding(bottom = 16.dp))

            Button(modifier = Modifier.padding(22.dp), onClick = { /*TODO*/ }) {
                Text(
                    text = "Obtener GeoLocalizacion",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun LocationScreenX() {
    var geoLocation = remember { mutableStateOf("Location not available") }
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = geoLocation.value, modifier = Modifier.padding(bottom = 16.dp))

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
                        // Do something with latitude and longitude, like display them
                        // For now, we just display it in a Text composable
                        geoLocation.value = "Latitude: $latitude, Longitude: $longitude"
                    } else {
                        geoLocation.value = "Location not available"
                    }
                }
        }) {
            Text(text = "Get Location")
        }
    }
}