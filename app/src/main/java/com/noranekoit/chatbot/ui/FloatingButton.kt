package com.noranekoit.chatbot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

import androidx.appcompat.app.ActionBar
import com.noranekoit.chatbot.R

class FloatingButton : AppCompatActivity(), View.OnClickListener {
    private var title: String = "Home"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floating_button)


        setActionBarTitle(title)

//        fab_btn.setOnClickListener {
//
//        }

//        val btnFab : FloatingActionButton = findViewById(R.id.fab_btn)

        val btnFab :Button = findViewById(R.id.fab_btn)
        btnFab.setOnClickListener(this)

        val btnAr : Button = findViewById(R.id.btn_ar)
        btnAr.setOnClickListener(this)

        val btnPetunjuk : Button = findViewById(R.id.btn_petunjuk)
        btnPetunjuk.setOnClickListener(this)
//        val btnFabVoice : FloatingActionButton = findViewById(R.id.fab_btn_voice)
//        btnFabVoice.setOnClickListener(this)



    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = title
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab_btn -> {
                val moveIntent = Intent(this, ChatBot::class.java)
                startActivity(moveIntent)
            }
            R.id.btn_ar -> {
                startActivity(Intent(this, AR::class.java))

            }
            R.id.btn_petunjuk ->{
                startActivity(Intent(this, Petunjuk::class.java))
            }
//            R.id.fab_btn_voice -> {
//                val moveIntent = Intent(this, SpeechText::class.java)
//                startActivity(moveIntent)
//            }
        }
    }
}