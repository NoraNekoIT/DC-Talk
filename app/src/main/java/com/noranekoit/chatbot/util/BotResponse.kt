package com.noranekoit.chatbot.util

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import com.noranekoit.chatbot.util.Constants.OPEN_GOOGLE
import com.noranekoit.chatbot.util.Constants.OPEN_SEARCH
import java.sql.Timestamp
import java.util.*

object BotResponse {

    @RequiresApi(Build.VERSION_CODES.N)
    fun basicResponse(_message: String):String{

        val random = (0..2).random()
        val message = _message.toLowerCase()

        return when{
            //Flips a coin
            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }

            //Math calculations
            message.contains("solve") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }
            //Hello
            message.contains("hello" )->{
                when(random){
                    0-> "Hello There!"
                    1-> "Hello Senpai"
                    2-> "Hello Sensei"
                    else -> "error"
                }
            }

            //How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }

            //What time is it?
            message.contains("time") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Open Google
            message.contains("open") && message.contains("google")-> {
                OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }



            else -> {
                when(random){
                    0 -> "I don't understand.."
                    1 -> "Try asking me something different"
                    2 -> "Pertanyaan terlalu sulit dibaca"
                    else-> "error"
                }
        }
        }


    }
}