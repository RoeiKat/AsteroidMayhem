package com.example.newlesson

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainMenuActivity : AppCompatActivity() {

    private lateinit var mainMenuFABPlay: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        findViews()
    }

    private fun findViews() {
        mainMenuFABPlay = findViewById(R.id.mainMenuFABPlay)
    }

    private fun initViews() {
        mainMenuFABPlay.setOnClickListener{ _: View -> moveToGameActivity()}
    }
    private fun moveToGameActivity() {
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
        finish()
    }
}