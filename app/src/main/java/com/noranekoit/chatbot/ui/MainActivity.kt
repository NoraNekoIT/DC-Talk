package com.noranekoit.chatbot.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.noranekoit.chatbot.R
import com.noranekoit.chatbot.data.Message
import com.noranekoit.chatbot.util.BotResponse
import com.noranekoit.chatbot.util.Constants.OPEN_GOOGLE
import com.noranekoit.chatbot.util.Constants.OPEN_SEARCH
import com.noranekoit.chatbot.util.Constants.RECEIVE_ID
import com.noranekoit.chatbot.util.Constants.SEND_ID
import com.noranekoit.chatbot.util.Time
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var title: String = "Chat Bot "
    private lateinit var adapter: MessagingAdapter
    private val botList = listOf("Ronald", "Bun","Chatrin")

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setActionBarTitle(title)

        reyclerView()

        clickEvents()

        val random = (0..3).random()
        customMessage("Hello!Today you're speaking with ${botList[random]}, how may I help?")



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

            delay(1000)

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

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount-1)
            }
        }
    }

    private fun customMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
               val timestamp = Time.timeStamp()
                adapter.insertMessage(Message(message, RECEIVE_ID, timestamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)

            }
        }

    }
}