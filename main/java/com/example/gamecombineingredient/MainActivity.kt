package com.example.gamecombineingredient

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.media.Image
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var playButton : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.playButton = findViewById(R.id.btn_play)
        playButton.setOnClickListener{
            val intent = Intent(this,GameModel::class.java)
            startActivity(intent)
        }

    }

}
