package com.example.gamecombineingredient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class GameModel : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(GameView(this))
    }
}