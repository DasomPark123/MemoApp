package com.example.memoapp.entity

//그림을 그릴 pen
class Pen(x : Float, y : Float, moveState : State, color : Int, size : Int) {
    enum class State {
        START, MOVE, END
    }
    val x : Float = x
    val y : Float = y
    val moveState : State = moveState
    val color : Int = color
    val size : Int = size

    fun move() : Boolean {
        return moveState == State.MOVE
    }
}