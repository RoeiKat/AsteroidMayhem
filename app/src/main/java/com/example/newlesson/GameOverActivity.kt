package com.example.newlesson

import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.newlesson.Util.LocationManager

import com.example.newlesson.Util.TimeFormatter

class GameOverActivity : AppCompatActivity() {
    private lateinit var locationManager: LocationManager

    //    private lateinit var gameOverTimeTXT: AppCompatTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)

        locationManager = LocationManager(this) { location ->
            if (location != null) {
                Toast.makeText(this, "Lat: ${location.latitude}, Long: ${location.longitude}", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Location not available", Toast.LENGTH_LONG).show()
            }
        }

        locationManager.startLocationUpdates()


    val timeStr = intent.extras?.getString("TIME_PASSED", "00:00:00") ?: "00:00:00"
    val timePassed = TimeFormatter.parseTimeToSeconds(timeStr)
    val highScoreManager = HighScoreManager(this)
    val highScores = highScoreManager.updateHighScores(timePassed)

    val highScoresDisplay = highScores.map {
        Log.d("HighScores", "Formatting time: $it")
        TimeFormatter.formatHighScoreTime(it) }




    supportFragmentManager.beginTransaction()
        .replace(R.id.highScoreContainer, HighScoreFragment.newInstance(ArrayList(highScoresDisplay)))
        .commit()

    }

}