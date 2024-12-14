package com.example.newlesson

import SignalManager
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView

import androidx.lifecycle.lifecycleScope
import com.example.newlesson.Logic.GameManager
import com.example.newlesson.Util.TimeFormatter
import com.example.newlesson.Util.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var gameTimerTXT: AppCompatTextView

    private var startTime: Long = 0

    private lateinit var timerJob: Job

    private fun updateTimerUI() {
        val currentTime = System.currentTimeMillis()
        gameTimerTXT.text = TimeFormatter.formatTime(currentTime - startTime)
    }

    private var lowerDelay: Long = 0
    private var secondsTillNextDelay: Long = 10;

    private lateinit var hearts: Array<AppCompatImageView>
    private lateinit var spaceShips: Array<AppCompatImageView>
    private lateinit var leftArrowBtn: AppCompatImageButton
    private lateinit var rightArrowBtn: AppCompatImageButton
    private lateinit var gameManager: GameManager
    private lateinit var orbitCols: Array<Array<AppCompatImageView>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mediaPlayer = MediaPlayer.create(this, R.raw.game_background_music)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        SignalManager.init(this)

        setContentView(R.layout.activity_game)

        findViews()
        gameManager = GameManager(hearts.size, orbitCols.size,orbitCols[0].size)
        initViews()
        startTimer()
    }

    private fun findViews() {
        hearts = arrayOf(
            findViewById(R.id.gameHeartIMG1),
            findViewById(R.id.gameHeartIMG2),
            findViewById(R.id.gameHeartIMG3)
        )
        spaceShips = arrayOf(
            findViewById(R.id.gameSpaceshipIMG1),
            findViewById(R.id.gameSpaceshipIMG2),
            findViewById(R.id.gameSpaceshipIMG3)
        )
        orbitCols = arrayOf(
            arrayOf(
                findViewById(R.id.gameOrbitIMG1),
                findViewById(R.id.gameOrbitIMG2),
                findViewById(R.id.gameOrbitIMG3),
                findViewById(R.id.gameOrbitIMG4),
                findViewById(R.id.gameOrbitIMG5)
            ),
            arrayOf(
                findViewById(R.id.gameOrbitIMG6),
                findViewById(R.id.gameOrbitIMG7),
                findViewById(R.id.gameOrbitIMG8),
                findViewById(R.id.gameOrbitIMG9),
                findViewById(R.id.gameOrbitIMG10)
            ),
            arrayOf(
                findViewById(R.id.gameOrbitIMG11),
                findViewById(R.id.gameOrbitIMG12),
                findViewById(R.id.gameOrbitIMG13),
                findViewById(R.id.gameOrbitIMG14),
                findViewById(R.id.gameOrbitIMG15)
            )
        )
        leftArrowBtn = findViewById(R.id.gameLeftBTN)
        rightArrowBtn = findViewById(R.id.gameRightBTN)
        gameTimerTXT = findViewById(R.id.gameTimerTXT)
    }

    private fun initViews() {
        for ((index, spaceShip) in spaceShips.withIndex()) {
            if (index != gameManager.getCurrentShipIndex()) {
                spaceShip.visibility = View.INVISIBLE
            }
        }
        for (orbitCol in orbitCols) {
            for(orbitImg in orbitCol) {
                orbitImg.visibility = View.INVISIBLE
            }
        }
        leftArrowBtn.setOnClickListener { _: View -> moveLeft() }
        rightArrowBtn.setOnClickListener { _: View -> moveRight() }
    }

    private fun moveRight() {
        gameManager.moveRight()
        refreshCarLocationUI()
    }

    private fun moveLeft() {
        gameManager.moveLeft()
        refreshCarLocationUI()
    }

    private fun startTimer() {
        startTime = System.currentTimeMillis()
        timerJob = lifecycleScope.launch {
            while (!gameManager.isGameOver) {
                val currentTime = System.currentTimeMillis()
                val seconds = ((currentTime - startTime) / 1000).toInt()
                if( seconds > secondsTillNextDelay && lowerDelay < 500L) {
                    gameManager.raiseChances()
                    secondsTillNextDelay = seconds + 10L;
                    lowerDelay += 100L
                }
                gameManager.activateRandomOrbit()
                refreshUI()
                delay(Constants.Timer.DELAY - 500L - lowerDelay)
                gameManager.orbitTick()
                gameManager.checkCollision(SignalManager.getInstance())
                refreshUI()
                delay(Constants.Timer.DELAY - lowerDelay)
            }
        }
    }

    private fun refreshOrbitLocationUI() {
        val orbitMatrix = gameManager.getOrbitMatrix()
        for((colIndex, _) in orbitMatrix.withIndex()) {
            for((rowIndex, _) in orbitMatrix[colIndex].withIndex()) {
                if(rowIndex <= orbitCols[0].size - 1) {
                    if(orbitMatrix[colIndex][rowIndex]) {
                        orbitCols[colIndex][rowIndex].visibility = View.VISIBLE
                    } else {
                        orbitCols[colIndex][rowIndex].visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun refreshCarLocationUI() {
        for ((index, spaceship) in spaceShips.withIndex()) {
            if (index == gameManager.getCurrentShipIndex()) {
                spaceship.visibility = View.VISIBLE
            } else {
                spaceship.visibility = View.INVISIBLE
            }
        }
    }

    private fun moveToGameOverActivity(){
        val currentTime = System.currentTimeMillis()
        val time: String = TimeFormatter.formatTime(currentTime - startTime)
        val intent = Intent(this, GameOverActivity::class.java)
        val bundle = Bundle()
        bundle.putString("TIME_PASSED", time)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    private fun refreshUI() {
        refreshOrbitLocationUI()
        updateTimerUI()
        if (gameManager.isGameOver) {
            mediaPlayer.stop()
            moveToGameOverActivity()
        }
        if (gameManager.hitCounter != 0) {
            hearts[hearts.size - gameManager.hitCounter].visibility =
                View.INVISIBLE
        }
    }
}