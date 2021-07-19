package com.noranekoit.chatbot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.cardview.widget.CardView
import com.noranekoit.chatbot.R

class Home : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val cvarbot : CardView =findViewById(R.id.cv_arbot)
        cvarbot.setOnClickListener(this)

        val cvchatbot : CardView =findViewById(R.id.cv_chatbot)
        cvchatbot.setOnClickListener(this)

        val cvinfo : CardView = findViewById(R.id.cv_info)
        cvinfo.setOnClickListener(this)

        val cvaboutus : CardView = findViewById(R.id.cv_aboutus)
        cvaboutus.setOnClickListener(this)

    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.cv_arbot -> {
                val moveIntent = Intent(this, AR::class.java)
                startActivity(moveIntent)
            }
            R.id.cv_chatbot -> {
                val moveIntent = Intent(this, ChatBot::class.java)
                startActivity(moveIntent)
            }
            R.id.cv_info -> {
                val moveIntent = Intent(this,Petunjuk::class.java)
                startActivity(moveIntent)
            }
            R.id.cv_aboutus -> {
                val moveIntent = Intent(this,AboutUs::class.java)
                startActivity(moveIntent)

            }

        }
    }
}