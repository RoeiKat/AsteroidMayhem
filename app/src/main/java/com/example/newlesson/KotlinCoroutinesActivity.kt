package com.example.newlesson

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.newlesson.objects.Constants
import com.example.newlesson.objects.TimeFormatter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class KotlinCoroutinesActivity : AppCompatActivity() {

//    private lateinit var main_FAB_play: ExtendedFloatingActionButton
//    private lateinit var main_FAB_stop: ExtendedFloatingActionButton
//    private lateinit var main_LBL_time: MaterialTextView
//
//    private lateinit var timerJob: Job
//
//    private var timerOn: Boolean = false
//    private var startTime:Long = 0
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//
//        findViews()
//        initViews()
//    }
//
//    private fun updateTimerUi() {
//        val currentTime = System.currentTimeMillis()
//        main_LBL_time.text = TimeFormatter.formatTime(currentTime - startTime)
//    }
//
//    private fun startTimer() {
//        if(!timerOn) {
//            timerOn = true
//            startTime = System.currentTimeMillis()
//            timerJob = lifecycleScope.launch {
//                while(timerOn) {
//                    updateTimerUi()
//                    delay(Constants.Timer.DELAY)
//                }
//            }
//        }
//    }
//
//    private fun stopTimer() {
//        timerOn = false
//        timerJob.cancel()
//    }
//
//    private fun findViews() {
//        main_LBL_time = findViewById(R.id.main_LBL_time)
//        main_FAB_play = findViewById(R.id.main_FAB_play)
//        main_FAB_stop = findViewById(R.id.main_FAB_stop)
//    }
//
//    private fun initViews() {
//        main_FAB_play.setOnClickListener { _ -> startTimer() }
//        main_FAB_stop.setOnClickListener { _ -> stopTimer() }
//    }
}