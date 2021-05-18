package com.noranekoit.chatbot.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.ActionBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.noranekoit.chatbot.R
import kotlinx.android.synthetic.main.activity_floating_button.*

class FloatingButton : AppCompatActivity(), View.OnClickListener {
    private var title: String = "Home"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floating_button)
        setActionBarTitle(title)

        val btnFab : FloatingActionButton = findViewById(R.id.fab_btn)
        btnFab.setOnClickListener(this)

        val btnFabVoice : FloatingActionButton = findViewById(R.id.fab_btn_voice)
        btnFabVoice.setOnClickListener(this)

    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = title
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.fab_btn -> {
                val moveIntent = Intent(this@FloatingButton, MainActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.fab_btn_voice -> {
                val moveIntent = Intent(this@FloatingButton, SpeechText::class.java)
                startActivity(moveIntent)
            }
        }
    }
}