package com.example.newlesson.Logic

import SignalManager

class GameManager(
    private val lifeCount: Int = 3,
    private val orbitCols: Int,
    private val orbitRows: Int,
) {
    private var currentShipIndex: Int = 1 // Starting from the middle of the array (SHIP LOC)
    private var orbitsMatrix: Array<Array<Boolean>> =
        Array(orbitCols) { Array(orbitRows + 1) { false } }

    private var upChances: Int = 0
    var hitCounter: Int = 0
    val isGameOver: Boolean
        get() = hitCounter == lifeCount



    public fun orbitTick() {
        for (colIndex in orbitsMatrix.indices) {
            for (rowIndex in orbitsMatrix[colIndex].size - 1 downTo 0) {
                if (orbitsMatrix[colIndex][rowIndex]) {
                    if (rowIndex < orbitsMatrix[colIndex].size - 1) {
                        orbitsMatrix[colIndex][rowIndex + 1] = true
                    }
                    orbitsMatrix[colIndex][rowIndex] = false
                }
            }
        }
    }

    public fun activateRandomOrbit() {
        val chance = 70 + upChances;
        if(chance + upChances > 100) {
            val randomColSecond = (0..orbitCols - 1).random()
            orbitsMatrix[randomColSecond][0] = true;
        }
        val randomCol = (0..orbitCols - 1).random()
        val randomNum = (0..100).random()
        val chancesToActivate = randomNum < chance
        if (chancesToActivate) {
            orbitsMatrix[randomCol][0] = true;
        }
    }

    public fun checkCollision(signalManager: SignalManager) {
        for(col in orbitsMatrix.indices) {
            for(row in orbitsMatrix[col].indices) {
                if(row == orbitsMatrix[col].size - 1 && orbitsMatrix[col][row] && col == currentShipIndex) {
                    signalManager.toast("Collision")
                    signalManager.vibrate()
                    hitCounter++
                }
            }
        }
    }


    public fun moveRight() {
        if (currentShipIndex > 0) {
            currentShipIndex--
        } else {
            // Out of bounds
            return
        }
    }

    public fun moveLeft() {
        if (currentShipIndex < 2) {
            currentShipIndex++
        } else {
            // Out of bounds
            return
        }
    }

    public fun raiseChances() {
        upChances+= 10
    }

    public fun getOrbitMatrix(): Array<Array<Boolean>> {
        return orbitsMatrix
    }

    public fun getCurrentShipIndex(): Int {
        return currentShipIndex
    }
}