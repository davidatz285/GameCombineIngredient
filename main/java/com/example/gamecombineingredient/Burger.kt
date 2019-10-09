package com.example.gamecombineingredient

import android.graphics.Rect
import java.lang.reflect.Constructor

class Burger(meat:Meat,bun:Bun,left:Int,top:Int,right:Int,bottom:Int) : Food(left,top,right,bottom) {
    var meat : Meat
    var bun : Bun
    init{
        this.meat = meat
        this.bun = bun
        super.rect = Rect(super.left,super.top,super.right,super.bottom)
        super.jenis = "burger"
    }



}
