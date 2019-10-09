package com.example.gamecombineingredient

import java.util.*

class Customer {
    val request : Int
    val random:Random

    init{
        random = Random()
        request = random.nextInt(2)
    }

    fun fillRequest(food: Food):Int{
        var poin : Int = 0

        if(request == 0){
            if(food.jenis == "Burger"){
                poin = 5
            }else{
                poin = -3
            }
        }else{
            if(food.jenis == "Sausage"){
                poin = 5
            }else{
                poin = -3
            }
        }
        return poin
    }
}