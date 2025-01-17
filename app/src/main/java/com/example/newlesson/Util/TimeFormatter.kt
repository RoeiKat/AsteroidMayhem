package com.example.newlesson.Util

import android.util.Log

object TimeFormatter {
    public fun parseTimeToSeconds(timeStr: String): Long {
        return try {
            val parts = timeStr.split(":").map { it.toInt() }
            val hours = parts[0]
            val minutes = parts[1]
            val seconds = parts[2]
            hours * 3600 + minutes * 60 + seconds.toLong()
        } catch (e: Exception) {
            0L
        }
    }
    public fun formatTime(currentTimeDelta: Long): String {
        var seconds = (currentTimeDelta / 1000).toInt()
        var minutes = seconds / 60
        seconds %= 60
        var hours = minutes / 60
        minutes %= 60
        hours %= 24

        return buildString {
            append(String.format(locale = null,"%02d", hours))
            append(":")
            append(String.format(locale = null,"%02d", minutes))
            append(":")
            append(String.format(locale = null,"%02d", seconds))
        }
    }

    fun formatHighScoreTime(seconds: Long): String {
        val hours = seconds / 3600
        val remainingSeconds = seconds % 3600
        val minutes = remainingSeconds / 60
        val secs = remainingSeconds % 60
        return buildString {
            append(String.format(locale = null, "%02d", hours))
            append(":")
            append(String.format(locale = null ,"%02d", minutes))
            append(":")
            append(String.format(locale = null,"%02d", secs))
        }    }
}