package com.example.gamecombineingredient

import android.graphics.Rect

open class Food(left: Int, top: Int, right: Int, bottom: Int) {
    var left: Int
    var top: Int
    var right: Int
    var bottom: Int
    var poin: Int = 0
    var jenis: String
    var rect: Rect

    init {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
        this.jenis = ""
        this.rect = Rect(left, top, right, bottom)
    }

    fun resetRect() {
        this.rect = Rect(left, top, right, bottom)
    }

}