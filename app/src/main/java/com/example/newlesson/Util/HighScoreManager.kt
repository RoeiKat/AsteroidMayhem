package com.example.newlesson

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HighScoreManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("high_scores", AppCompatActivity.MODE_PRIVATE)
    private val gson = Gson()

    fun saveHighScores(highScores: ArrayList<Long>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(highScores)
        editor.putString("high_scores", json)
        editor.apply()
    }

    fun getHighScores(): ArrayList<Long> {
        val json = sharedPreferences.getString("high_scores", null)
        val type = object : TypeToken<ArrayList<Long>>() {}.type
        return gson.fromJson(json, type) ?: ArrayList()
    }

    fun updateHighScores(newScore: Long): ArrayList<Long> {
        val highScores = getHighScores()
        highScores.add(newScore)
        highScores.sortDescending()
        if (highScores.size > 5) {
            highScores.removeAt(highScores.size - 1)
        }
        saveHighScores(highScores)
        return highScores
    }
}
