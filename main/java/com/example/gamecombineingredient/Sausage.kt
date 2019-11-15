package com.example.gamecombineingredient

import android.graphics.Rect

class Sausage(left:Int,top:Int,right:Int,bottom:Int): Food(left, top, right, bottom) {


    init{
        super.left = left
        super.top = top
        super.right = right
        super.bottom = bottom
        super.rect = Rect(left,top,right,bottom)
        super.jenis = "sausage"
        super.poin = 3
    }

}