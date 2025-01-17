package com.example.newlesson

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
import com.example.newlesson.Util.LocationManager

class MainMenuActivity : AppCompatActivity() {
    private lateinit var locationManager: LocationManager

    private lateinit var mainMenuFABPlay: AppCompatButton

    private lateinit var mainMenuTiltModeSwitch: SwitchCompat

    private var tiltMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        findViews()
        initViews()

        locationManager = LocationManager(this, { _ ->
        })
        locationManager.checkPermissionsAndStartLocationUpdates()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun findViews() {
        mainMenuFABPlay = findViewById(R.id.mainMenuFABPlay)
        mainMenuTiltModeSwitch = findViewById(R.id.mainMenuTiltModeSwitch)
    }

    private fun initViews() {
        mainMenuFABPlay.setOnClickListener{ _: View -> moveToGameActivity()}
    }

    private fun moveToGameActivity() {
        tiltMode = mainMenuTiltModeSwitch.isChecked
        val bundle = Bundle()
        val intent = Intent(this, GameActivity::class.java)
        bundle.putBoolean("TILT_MODE", tiltMode)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
}