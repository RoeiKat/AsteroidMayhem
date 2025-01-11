package com.example.newlesson.Logic

import SignalManager
import android.opengl.Matrix
import com.example.newlesson.Util.MatrixObjects

class GameManager(
    private val lifeCount: Int = 3,
    private val matrixCols: Int,
    private val matrixRows: Int,
    private val spaceshipRows: Int = 5,
) {
    private var currentShipIndex: Int = 2 // Starting from the middle of the array (SHIP LOC)
    private var gameMatrix: Array<Array<MatrixObjects>> =
        Array(matrixCols) { Array(matrixRows + 1) { MatrixObjects.EMPTY } }

    private var upChances: Int = 0
    var hitCounter: Int = 0
    val isGameOver: Boolean
        get() = hitCounter == lifeCount



    public fun matrixTick() {
        for (colIndex in gameMatrix.indices) {
            for (rowIndex in gameMatrix[colIndex].size - 1 downTo 0) {
                if (gameMatrix[colIndex][rowIndex] != MatrixObjects.EMPTY) {
                    if (rowIndex < gameMatrix[colIndex].size - 1) {
                        gameMatrix[colIndex][rowIndex + 1] = gameMatrix[colIndex][rowIndex]
                    }
                    gameMatrix[colIndex][rowIndex] = MatrixObjects.EMPTY
                }
            }
        }
    }


    public fun activateRandomCell() {
        val orbitChance = 70 + upChances
        val heartChance = 50
        if(orbitChance + upChances > 100) {
            val randomColSecond = (0..matrixCols - 1).random()
            if(gameMatrix[randomColSecond][0] == MatrixObjects.EMPTY) {
                gameMatrix[randomColSecond][0] = MatrixObjects.ORBIT
            }
        }
        val randomCol = (0..matrixCols - 1).random()
        val randomOrbitNum = (0..100).random()
        val randomHeartNum = (0..100).random()

        val heartOrOrbit = randomHeartNum > randomOrbitNum
        if(heartOrOrbit) {
            val chancesToActivateHeart = randomHeartNum < heartChance
            if(chancesToActivateHeart) {
                gameMatrix[randomCol][0] = MatrixObjects.HEART
            }
        } else {
            val chancesToActivateOrbit = randomOrbitNum < orbitChance
            if (chancesToActivateOrbit) {
                  gameMatrix[randomCol][0] = MatrixObjects.ORBIT;
            }
        }
    }

    public fun checkCollision(signalManager: SignalManager) {
        for(col in gameMatrix.indices) {
            for(row in gameMatrix[col].indices) {
                if(row == gameMatrix[col].size - 1 && gameMatrix[col][row] != MatrixObjects.EMPTY && col == currentShipIndex) {
                    if(gameMatrix[col][row] == MatrixObjects.ORBIT) {
                    signalManager.toast("Collision")
                    signalManager.vibrate()
                    hitCounter++
                    } else if(gameMatrix[col][row] == MatrixObjects.HEART && hitCounter > 0) {
                        signalManager.toast("Extra life!")
                        signalManager.vibrate()
                        hitCounter --
                    } else {
                        return
                    }
                }
            }
        }
    }


    public fun moveRight() {
        if (currentShipIndex < spaceshipRows - 1) {
            currentShipIndex++
        } else {
            return
        }
    }

    public fun moveLeft() {
        if (currentShipIndex > 0 ) {
            currentShipIndex--
        } else {
            return
        }
    }

    public fun raiseChances() {
        upChances+= 10
    }

    public fun getGameMatrix(): Array<Array<MatrixObjects>> {
        return gameMatrix
    }

    public fun getCurrentShipIndex(): Int {
        return currentShipIndex
    }
}