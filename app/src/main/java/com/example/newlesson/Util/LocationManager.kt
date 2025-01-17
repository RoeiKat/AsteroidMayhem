package com.example.newlesson.Util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationManager(private val context: Context, private val locationUpdateListener: (Location?) -> Unit) {
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    companion object {
        private const val LOCATION_PERMISSION_REQ_CODE = 100
    }

    fun checkPermissionsAndStartLocationUpdates() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (context is Activity) {
                ActivityCompat.requestPermissions(context,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQ_CODE)
            }
        } else {
            startLocationUpdates()
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQ_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                locationUpdateListener(null)
            }
        }
    }

    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    locationUpdateListener(location)
                }
                .addOnFailureListener {
                    locationUpdateListener(null)
                }
        }
    }
}
