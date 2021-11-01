package com.example.memoapp.entity

import android.graphics.Path

//그림을 그릴 pen
class Pen( color : Int, size : Int) {
    enum class State {
        START, MOVE, END
    }
//    val x : Float = x
//    val y : Float = y
    val path : Path = Path()
    var color : Int = color
    var size : Int = size
}