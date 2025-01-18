package com.example.newlesson

import HighScoreManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.newlesson.Util.LocationManager

import com.example.newlesson.Util.TimeFormatter
import com.example.newlesson.objects.Constants
import com.example.newlesson.objects.HighScoreData

class GameOverActivity : AppCompatActivity() {
    private lateinit var locationManager: LocationManager
    private lateinit var highScoreManager: HighScoreManager
    private var timePassed: Long = 0
    private var playerName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        highScoreManager = HighScoreManager(this)
        timePassed = TimeFormatter.parseTimeToSeconds(intent.extras?.getString(Constants.Keys.TIME_PASSED, "00:00:00") ?: "00:00:00")
        playerName = intent.extras?.getString(Constants.Keys.PLAYER_NAME, "Player") ?: "Player"

        locationManager = LocationManager(this) { location ->
            if (location != null) {
                val newHighScore = HighScoreData(playerName, timePassed, location.latitude, location.longitude)
                val highScores = highScoreManager.updateHighScores(newHighScore)
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

}