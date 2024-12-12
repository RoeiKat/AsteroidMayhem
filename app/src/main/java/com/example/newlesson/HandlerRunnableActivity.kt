package com.example.newlesson

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.newlesson.objects.Constants
import com.example.newlesson.objects.TimeFormatter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textview.MaterialTextView

class HandlerRunnableActivity : AppCompatActivity() {

    private lateinit var main_FAB_play: ExtendedFloatingActionButton
    private lateinit var main_FAB_stop: ExtendedFloatingActionButton
    private lateinit var main_LBL_time: MaterialTextView

    val handler: Handler = Handler(Looper.getMainLooper())
    private var timerOn: Boolean = false
    private var startTime:Long = 0

    private val runnable: Runnable = object: Runnable {
        override fun run() {
            //reschedule
            handler.postDelayed(this, Constants.Timer.DELAY)

            //refresh ui
            updateTimerUi()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        findViews()
        initViews()
    }

    private fun updateTimerUi() {
        val currentTime = System.currentTimeMillis()
        main_LBL_time.text = TimeFormatter.formatTime(currentTime - startTime)
    }

    private fun startTimer() {
        if(!timerOn) {
            startTime = System.currentTimeMillis()
            handler.postDelayed(runnable, Constants.Timer.DELAY)
            timerOn = true
        }
    }

    private fun stopTimer() {
        handler.removeCallbacks(runnable)
        timerOn = false
    }

    private fun findViews() {
        main_LBL_time = findViewById(R.id.main_LBL_time)
        main_FAB_play = findViewById(R.id.main_FAB_play)
        main_FAB_stop = findViewById(R.id.main_FAB_stop)
    }

    private fun initViews() {
        main_FAB_play.setOnClickListener { _ -> startTimer() }
        main_FAB_stop.setOnClickListener { _ -> stopTimer() }
    }
}