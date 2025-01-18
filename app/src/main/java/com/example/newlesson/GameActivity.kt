package com.example.newlesson

import SignalManager
import android.content.Intent
import android.graphics.drawable.Drawable
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
import com.example.newlesson.Interfaces.TiltCallback
import com.example.newlesson.Logic.GameManager
import com.example.newlesson.Util.TimeFormatter
import com.example.newlesson.Util.MatrixObjects
import com.example.newlesson.Util.TiltDetector
import com.example.newlesson.objects.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {
    private var playerName: String? = ""

    private var tiltMode: Boolean = false

    private lateinit var tiltDetector: TiltDetector

    private var lastTilt: Int = 0

//    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var gameTimerTXT: AppCompatTextView

    private var startTime: Long = 0

    private var pauseTime: Long = 0
    private var pauseDuration: Long = 0

    private var elapsedTime: Long = 0

    private lateinit var timerJob: Job

    private var lowerDelay: Long = 0
    private var secondsTillNextDelay: Long = 10;

    private lateinit var hearts: Array<AppCompatImageView>
    private lateinit var spaceShips: Array<AppCompatImageView>
    private lateinit var leftArrowBtn: AppCompatImageButton
    private lateinit var rightArrowBtn: AppCompatImageButton
    private lateinit var gameManager: GameManager
    private lateinit var viewsMatrix: Array<Array<AppCompatImageView>>

    private val orbitIcon: Int = R.drawable.avoid_the_rocks_orbit
    private val heartIcon: Int = R.drawable.avoid_the_rocks_heart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val bundle: Bundle? = intent.extras

        playerName = bundle?.getString(Constants.Keys.PLAYER_NAME,"Player")
        tiltMode = bundle?.getBoolean(Constants.Keys.TILT_MODE, false)!!

//        mediaPlayer = MediaPlayer.create(this, R.raw.game_background_music)
//        mediaPlayer.isLooping = true

        if(tiltMode) {
        initTiltDetector()
        }

        SignalManager.init(this)

        setContentView(R.layout.activity_game)

        findViews()
        gameManager = GameManager(hearts.size, viewsMatrix.size,viewsMatrix[0].size, spaceShips.size)
        initViews()
        startTimer()
    }
    override fun onPause() {
        super.onPause()
        if(tiltMode) {
        tiltDetector.stop()
        }
//        mediaPlayer.pause()
        timerJob.cancel()
        pauseTime = System.currentTimeMillis()
    }


    override fun onResume() {
        super.onResume()
        if(tiltMode) {
        tiltDetector.start()
        }
//        mediaPlayer.start()
        val currentTime = System.currentTimeMillis()
        if (pauseTime > 0) { // Ensure pauseTime is valid
            pauseDuration += (currentTime - pauseTime)
        }
        timerJob = lifecycleScope.launch {
            while (!gameManager.isGameOver) {
                increaseIntensity()
                gameManager.matrixTick()
                gameManager.activateRandomCell()
                gameManager.checkCollision(SignalManager.getInstance())
                refreshUI()
                delay(Constants.Timer.DELAY - lowerDelay)
            }
        }
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
            findViewById(R.id.gameSpaceshipIMG3),
            findViewById(R.id.gameSpaceshipIMG4),
            findViewById(R.id.gameSpaceshipIMG5)
        )
        viewsMatrix = arrayOf(
            arrayOf(
                findViewById(R.id.gameMatrixIMG1),
                findViewById(R.id.gameMatrixIMG2),
                findViewById(R.id.gameMatrixIMG3),
                findViewById(R.id.gameMatrixIMG4),
                findViewById(R.id.gameMatrixIMG5)
            ),
            arrayOf(
                findViewById(R.id.gameMatrixIMG6),
                findViewById(R.id.gameMatrixIMG7),
                findViewById(R.id.gameMatrixIMG8),
                findViewById(R.id.gameMatrixIMG9),
                findViewById(R.id.gameMatrixIMG10)
            ),
            arrayOf(
                findViewById(R.id.gameMatrixIMG11),
                findViewById(R.id.gameMatrixIMG12),
                findViewById(R.id.gameMatrixIMG13),
                findViewById(R.id.gameMatrixIMG14),
                findViewById(R.id.gameMatrixIMG15)
            ),
            arrayOf(
                findViewById(R.id.gameMatrixIMG16),
                findViewById(R.id.gameMatrixIMG17),
                findViewById(R.id.gameMatrixIMG18),
                findViewById(R.id.gameMatrixIMG19),
                findViewById(R.id.gameMatrixIMG20)
            ),
            arrayOf(
                findViewById(R.id.gameMatrixIMG21),
                findViewById(R.id.gameMatrixIMG22),
                findViewById(R.id.gameMatrixIMG23),
                findViewById(R.id.gameMatrixIMG24),
                findViewById(R.id.gameMatrixIMG25)
            ),

        )
        leftArrowBtn = findViewById(R.id.gameLeftArrowBTN)
        rightArrowBtn = findViewById(R.id.gameRightArrowBTN)
        gameTimerTXT = findViewById(R.id.gameTimerTXT)
    }

    private fun initViews() {
        for ((index, spaceShip) in spaceShips.withIndex()) {
            if (index != gameManager.getCurrentShipIndex()) {
                spaceShip.visibility = View.INVISIBLE
            }
        }
        for (viewsMatrixCol in viewsMatrix) {
            for(viewsMatrixImg in viewsMatrixCol) {
                viewsMatrixImg.visibility = View.INVISIBLE
            }
        }
        if(tiltMode) {
            leftArrowBtn.visibility = View.INVISIBLE
            rightArrowBtn.visibility = View.INVISIBLE
        } else {
            leftArrowBtn.setOnClickListener { _: View -> moveLeft() }
            rightArrowBtn.setOnClickListener { _: View -> moveRight() }
        }
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
        pauseDuration = 0
    }

    private fun increaseIntensity() {
        val currentTime = System.currentTimeMillis()
        val elapsedSeconds = (currentTime - startTime - pauseDuration) / 1000

        if( elapsedSeconds >= secondsTillNextDelay && lowerDelay < 500) {
            gameManager.raiseChances()
            secondsTillNextDelay += 20;
            lowerDelay += 100
        }
    }

    private fun initTiltDetector() {
        tiltDetector = TiltDetector(
            context = this,
            tiltCallback = object : TiltCallback {
                override fun tiltX() {
                    tiltDetector.tiltCounterX.toString().also {
                        if(tiltDetector.tiltCounterX > lastTilt) {
                            moveLeft()
                            lastTilt = tiltDetector.tiltCounterX
                        } else if(tiltDetector.tiltCounterX < lastTilt) {
                            moveRight()
                            lastTilt = tiltDetector.tiltCounterX
                        }
                    }
                }
                override fun tiltY() {
                    tiltDetector.tiltCounterY.toString()
                }
            }
        )
    }

    private fun refreshOrbitLocationUI() {
        val gameMatrix = gameManager.getGameMatrix()
        for((colIndex, _) in gameMatrix.withIndex()) {
            for((rowIndex, _) in gameMatrix[colIndex].withIndex()) {
                if(rowIndex <= viewsMatrix[0].size - 1) {
                    if(gameMatrix[colIndex][rowIndex] != MatrixObjects.EMPTY) {
                        if(gameMatrix[colIndex][rowIndex] == MatrixObjects.ORBIT) {
                            viewsMatrix[colIndex][rowIndex].setImageResource(orbitIcon)
                        } else if(gameMatrix[colIndex][rowIndex] == MatrixObjects.HEART) {
                            viewsMatrix[colIndex][rowIndex].setImageResource(heartIcon)
                        }
                        viewsMatrix[colIndex][rowIndex].visibility = View.VISIBLE
                    } else {
                        viewsMatrix[colIndex][rowIndex].visibility = View.INVISIBLE
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
        val time: String = TimeFormatter.formatTime(elapsedTime)
        val intent = Intent(this, GameOverActivity::class.java)
        val bundle = Bundle()
        bundle.putString(Constants.Keys.TIME_PASSED, time)
        bundle.putBoolean(Constants.Keys.TILT_MODE, tiltMode)
        bundle.putString(Constants.Keys.PLAYER_NAME, playerName)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }

    private fun refreshUI() {
        updateTimerUI()
        refreshOrbitLocationUI()
        if (gameManager.isGameOver) {
//            mediaPlayer.stop()
            moveToGameOverActivity()
        }
        for (index in hearts.indices) {
            if (index < hearts.size - gameManager.hitCounter) {
                hearts[index].visibility = View.VISIBLE
            } else {
                hearts[index].visibility = View.INVISIBLE
            }
        }
    }

    private fun updateTimerUI() {
        val currentTime = System.currentTimeMillis()
        elapsedTime = currentTime - startTime - pauseDuration
        gameTimerTXT.text = TimeFormatter.formatTime(elapsedTime)
    }
}