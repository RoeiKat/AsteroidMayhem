package com.example.newlesson

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.SwitchCompat
import com.example.newlesson.Util.LocationManager
import com.example.newlesson.objects.Constants

class MainMenuActivity : AppCompatActivity() {
    private var playerName: String = ""
    private var tiltMode: Boolean = false

    private lateinit var locationManager: LocationManager

    private lateinit var mainMenuFABPlay: AppCompatButton

    private lateinit var mainMenuTiltModeSwitch: SwitchCompat

    private lateinit var inputPlayerEditText: AppCompatEditText


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
        inputPlayerEditText = findViewById(R.id.inputPlayerEditText)

        inputPlayerEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                val isEmpty = s.toString().trim().isEmpty()
                mainMenuFABPlay.isEnabled = !isEmpty
                if(!isEmpty) {
                    mainMenuFABPlay.setBackgroundColor(getColor(R.color.black))
                } else {
                    mainMenuFABPlay.setBackgroundColor(getColor(R.color.grey))
                }
            }
        })
        mainMenuFABPlay.setOnClickListener{ _: View -> moveToGameActivity()}
    }

    private fun moveToGameActivity() {
        playerName = inputPlayerEditText.text.toString()
        if(playerName.isEmpty()) {
            return
        }
        tiltMode = mainMenuTiltModeSwitch.isChecked
        val bundle = Bundle()
        val intent = Intent(this, GameActivity::class.java)
        bundle.putBoolean(Constants.Keys.TILT_MODE, tiltMode)
        bundle.putString(Constants.Keys.PLAYER_NAME, playerName)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
}