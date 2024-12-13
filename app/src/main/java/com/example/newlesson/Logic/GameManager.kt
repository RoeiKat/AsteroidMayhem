package com.example.newlesson.Logic

import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView

class GameManager(private val spaceShips: Array<AppCompatImageView>,
                  private val lifeCount: Int = 3
) {
    var currentShipIndex: Int = 1 // Starting from the middle of the array





    public fun moveRight() {
        if(currentShipIndex > 0) {
            currentShipIndex--;
        } else {
            // Out of bounds
            return
        }
    }
    public fun moveLeft() {
        if(currentShipIndex < 2) {
            currentShipIndex++;
        } else {
            // Out of bounds
            return
        }
    }
}