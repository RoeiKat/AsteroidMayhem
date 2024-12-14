package com.example.newlesson

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MainMenuActivity : AppCompatActivity() {
    private lateinit var mainMenuFABPlay: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        findViews()
        initViews()
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