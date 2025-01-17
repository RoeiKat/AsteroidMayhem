package com.example.newlesson.objects

import java.io.Serializable

data class HighScoreData(
    val score: Long,
    val latitude: Double,
    val longitude: Double
) : Serializable
