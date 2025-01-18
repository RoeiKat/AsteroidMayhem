package com.example.newlesson.objects

import java.io.Serializable

data class HighScoreData(
    val playerName: String,
    val score: Long,
    val latitude: Double,
    val longitude: Double
) : Serializable
