package com.noranekoit.chatbot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.noranekoit.chatbot.R

class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIME :Long =3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed(
            {
                startActivity(Intent(this, FloatingButton::class.java))
                finish()
            },SPLASH_TIME
        )
    }
}