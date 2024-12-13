package com.example.newlesson

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.ImageViewCompat
import com.example.newlesson.Logic.GameManager

class GameActivity : AppCompatActivity() {

    private lateinit var hearts: Array<AppCompatImageView>
    private lateinit var spaceShips: Array<AppCompatImageView>
    private lateinit var leftArrowBtn: AppCompatImageButton
    private lateinit var rightArrowBtn: AppCompatImageButton
    private lateinit var gameManager: GameManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        findViews()
        gameManager = GameManager(spaceShips)
        initViews()
    }

    private fun findViews() {
        hearts = arrayOf(findViewById(R.id.gameHeartIMG1),
            findViewById(R.id.gameHeartIMG2),
            findViewById(R.id.gameHeartIMG3))
        spaceShips = arrayOf(findViewById(R.id.gameSpaceshipIMG1),
            findViewById(R.id.gameSpaceshipIMG2),
            findViewById(R.id.gameSpaceshipIMG3))
        leftArrowBtn = findViewById(R.id.gameLeftBTN)
        rightArrowBtn = findViewById(R.id.gameRightBTN)
    }

    private fun initViews() {
        for((index, value ) in spaceShips.withIndex()) {
            if(index != gameManager.currentShipIndex ) {
                value.visibility = View.INVISIBLE
            }
        }
        leftArrowBtn.setOnClickListener{_:View -> moveLeft()}
        rightArrowBtn.setOnClickListener{_:View -> moveRight()}
    }

    private fun moveRight() {
        gameManager.moveRight()
        refreshCarLocationUI()
    }

    private fun moveLeft() {
        gameManager.moveLeft()
        refreshCarLocationUI()
    }

    private fun refreshCarLocationUI() {
        for((index, spaceship ) in spaceShips.withIndex()) {
            if(index == gameManager.currentShipIndex) {
                spaceship.visibility = View.VISIBLE
            } else {
                spaceship.visibility = View.INVISIBLE
            }
        }
    }

    private fun refreshUI() {
        TODO()
    }
}