package com.example.newlesson

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat

class MainMenuActivity : AppCompatActivity() {
    private lateinit var mainMenuFABPlay: AppCompatButton

    private lateinit var mainMenuTiltModeSwitch: SwitchCompat

    private var tiltMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        findViews()
        initViews()
    }

    private fun findViews() {
        mainMenuFABPlay = findViewById(R.id.mainMenuFABPlay)
        mainMenuTiltModeSwitch = findViewById(R.id.mainMenuTiltModeSwitch)
    }

    private fun initViews() {
        mainMenuFABPlay.setOnClickListener{ _: View -> moveToGameActivity()}
        mainMenuTiltModeSwitch.setOnClickListener{_: View -> switchHandler()}
    }

    private fun switchHandler() {
        tiltMode = mainMenuTiltModeSwitch.isChecked
    }

    private fun moveToGameActivity() {
        val intent = Intent(this, GameActivity::class.java)
        val bundle = Bundle()
        bundle.putBoolean("TILT_MODE", tiltMode)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
}