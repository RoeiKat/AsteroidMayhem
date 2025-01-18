package com.example.newlesson

import HighScoreManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.newlesson.Interfaces.MapLocationUpdater
import com.example.newlesson.Util.LocationManager

import com.example.newlesson.Util.TimeFormatter
import com.example.newlesson.objects.Constants
import com.example.newlesson.objects.HighScoreData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GameOverActivity : AppCompatActivity(),OnMapReadyCallback, MapLocationUpdater {
    private lateinit var locationManager: LocationManager
    private lateinit var highScoreManager: HighScoreManager
    private var timePassed: Long = 0
    private var playerName: String = ""
    private lateinit var mMap: GoogleMap
    private var lastLocation = LatLng(-34.0, 151.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        highScoreManager = HighScoreManager(this)
        timePassed = TimeFormatter.parseTimeToSeconds(intent.extras?.getString(Constants.Keys.TIME_PASSED, "00:00:00") ?: "00:00:00")
        playerName = intent.extras?.getString(Constants.Keys.PLAYER_NAME, "Player") ?: "Player"

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationManager = LocationManager(this) { location ->
            if (location != null) {
                val newHighScore = HighScoreData(playerName, timePassed, location.latitude, location.longitude)
                val highScores = highScoreManager.updateHighScores(newHighScore)
                lastLocation = LatLng(location.latitude, location.longitude)

                runOnUiThread {
                    if (::mMap.isInitialized) {
                        updateMapLocation(lastLocation)
                    }
                }

                updateHighScoreDisplay(highScores)
            } else {
                Toast.makeText(this, "Location not available", Toast.LENGTH_LONG).show()
            }
        }
        locationManager.startLocationUpdates()
    }


    private fun updateHighScoreDisplay(highScores: ArrayList<HighScoreData>) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.highScoreContainer, HighScoreFragment.newInstance(highScores))
            .commit()
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        mMap.isMyLocationEnabled = true

        updateMapLocation(lastLocation)

    }

    private fun updateMapLocation(location: LatLng) {
        if (::mMap.isInitialized) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))

            mMap.clear()

            mMap.addMarker(
                MarkerOptions()
                .position(location)
                .title("High Score Location")  // Optional: set a title for the marker
                .snippet("The High Score was achieved here!"))  // Optional: set a subtitle
        } else {
            Log.d("GameOverActivity", "Map not initialized")
        }
    }

    override fun updateMapToLocation(lat: Double, lng: Double) {
        updateMapLocation(LatLng(lat, lng))
    }

}