package com.example.newlesson

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameOverActivity : AppCompatActivity() {
    private lateinit var gameOverTimeTXT: AppCompatTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_over)
        val bundle: Bundle? = intent.extras
        gameOverTimeTXT = findViewById(R.id.gameOverTimeTXT)
        gameOverTimeTXT.text = bundle?.getString("TIME_PASSED", "Unknown")

    }
}