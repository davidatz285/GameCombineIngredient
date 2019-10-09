package com.example.gamecombineingredient

import android.graphics.Rect

class Hotdog(sausage:Sausage,bun:Bun,left:Int,top:Int,right:Int,bottom:Int) : Food(left,top,right,bottom) {
    var sausage : Sausage
    var bun : Bun

    init{
        this.sausage = sausage
        this.bun = bun
        super.rect = Rect(super.left,super.top,super.right,super.bottom)
        super.jenis = "hotdog"
    }



}