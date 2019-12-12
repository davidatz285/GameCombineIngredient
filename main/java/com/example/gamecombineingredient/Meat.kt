package com.example.gamecombineingredient

import android.content.Context
import android.graphics.Rect
import android.widget.ImageView

class Meat(left: Int, top: Int, right: Int, bottom: Int) : Food(left, top, right, bottom) {
    var counterCook: Int

    init {
        super.poin = 5
        super.left = left
        super.top = top
        super.right = right
        super.bottom = bottom
        super.rect = Rect(left, top, right, bottom)
        super.jenis = "meat"
        super.isCooked = false
        this.counterCook = 0
    }

}