package com.example.gamecombineingredient

class Position(x1: Int, y1: Int, x2: Int, y2: Int) {
    var left: Int
    var top: Int
    var right: Int
    var bottom: Int

    init {
        this.left = x1
        this.top = y1
        this.right = x2
        this.bottom = y2
    }
}