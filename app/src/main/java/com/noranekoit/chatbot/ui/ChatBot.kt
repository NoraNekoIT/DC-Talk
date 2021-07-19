package com.noranekoit.chatbot.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.noranekoit.chatbot.R
import com.noranekoit.chatbot.data.Message
import com.noranekoit.chatbot.util.BotResponse
import com.noranekoit.chatbot.util.Constants.BIAYA_KULIAH
import com.noranekoit.chatbot.util.Constants.INFO_BEASISWA
import com.noranekoit.chatbot.util.Constants.OPEN_GOOGLE
import com.noranekoit.chatbot.util.Constants.OPEN_SEARCH
import com.noranekoit.chatbot.util.Constants.RECEIVE_ID
import com.noranekoit.chatbot.util.Constants.SEND_ID
import com.noranekoit.chatbot.util.Time
import kotlinx.android.synthetic.main.activity_chatbot.*


import kotlinx.coroutines.*
import java.util.*

class ChatBot : AppCompatActivity() {
    private var title: String = "Chat Bot "
    private lateinit var adapter: MessagingAdapter
    private val botList = listOf("Ronald", "Bun","Chatrin")

    //voice
    private val RQ_SPEECH_REC =102


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        btn_send.setVisibility(View.INVISIBLE)
        setActionBarTitle(title)

        reyclerView()

        clickEvents()

        val random = (0..3).random()
        customMessage("Hello!kali ini kamu berbicara dengan ${botList[random]}, ada yang bisa dibantu?")

//        et_message.addTextChangedListener(object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable) {}
//
//            override fun beforeTextChanged(s: CharSequence, start: Int,
//                                           count: Int, after: Int) {
//                if (s.toString().trim().length ==0){
//                    btn_voice.setVisibility(View.VISIBLE)
//                    btn_send.setVisibility(View.INVISIBLE)
//                }
//                else{
//                    btn_send.setVisibility(View.VISIBLE)
//                    btn_voice.setVisibility(View.INVISIBLE)
//                }
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int,
//                                       before: Int, count: Int) {
//            }
//        })



    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = title
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun clickEvents(){
        btn_send.setOnClickListener {
            sendMessage()
        }
        //voice
        btn_voice.setOnClickListener {
            askSpeechInput()

        }

        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount-1)
                }
            }

        }

    }

    private  fun reyclerView(){
        adapter = MessagingAdapter()
        rv_messages.adapter =adapter
        rv_messages.layoutManager =LinearLayoutManager(applicationContext)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private  fun sendMessage(){
        val message = et_message.text.toString()
        val timestamp = Time.timeStamp()

        if(message.isNotEmpty()){
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID,timestamp))
            rv_messages.scrollToPosition(adapter.itemCount-1)

            botResponse(message)
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun botResponse(message: String){
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {

            delay(200)

            withContext(Dispatchers.Main) {

                val response = BotResponse.basicResponse(message)

                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount - 1)

                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                    INFO_BEASISWA -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.ukdc.ac.id/?page_id=3959")
                        startActivity(site)
                    }
                    BIAYA_KULIAH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.ukdc.ac.id/penmaru/?page_id=28")
                        startActivity(site)
                    }



                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            delay(200)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }

    private fun customMessage(message: String) {
        GlobalScope.launch {
            delay(200)
            withContext(Dispatchers.Main){
               val timestamp = Time.timeStamp()
                adapter.insertMessage(Message(message, RECEIVE_ID, timestamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)

            }
        }

    }


    //voice
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val tempVoice = result?.get(0).toString()

            val message = tempVoice
            val timestamp = Time.timeStamp()

            if(message.isNotEmpty()){
                et_message.setText("")

                adapter.insertMessage(Message(message, SEND_ID,timestamp))
                rv_messages.scrollToPosition(adapter.itemCount-1)

                botResponse(message)
            }
        }
    }
        private fun askSpeechInput() {
            if (!SpeechRecognizer.isRecognitionAvailable(this)) {
                Toast.makeText(this, "Speech Recognition is not Available", Toast.LENGTH_SHORT)
                    .show()

            } else {
                val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                i.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something!")
                startActivityForResult(i, RQ_SPEECH_REC)
            }

        }

}