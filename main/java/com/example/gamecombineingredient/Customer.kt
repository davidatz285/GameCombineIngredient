package com.example.gamecombineingredient

import android.graphics.Rect
import java.util.*

class Customer(left: Int, top: Int, right: Int, bottom: Int) {
    val request: Int
    val random: Random
    val left: Int
    val top: Int
    val right: Int
    val bottom: Int
    val rect : Rect

    init {
        random = Random()
        request = random.nextInt(2)
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
        this.rect = Rect(left,top,right,bottom)
    }

    fun fillRequest(food: Food): Int {
        var poin: Int = 0

        if (request == 0) {
            if (food.jenis == "burger") {
                poin = 5
            } else {
                poin = -3
            }
        } else {
            if (food.jenis == "hotdog") {
                poin = 5
            } else {
                poin = -3
            }
        }
        return poin
    }
}