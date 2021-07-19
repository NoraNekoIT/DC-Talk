package com.noranekoit.chatbot.util

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import com.noranekoit.chatbot.util.Constants.BIAYA_KULIAH
import com.noranekoit.chatbot.util.Constants.INFO_BEASISWA
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
//            message.contains("flip") && message.contains("coin") -> {
//                val r = (0..1).random()
//                val result = if (r == 0) "heads" else "tails"
//
//                "I flipped a coin and it landed on $result"
//            }

            //Math calculations
            message.contains("hitung") -> {
                val equation: String? = message.substringAfterLast("hitung")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Maaf, aku gak bisa hitung itu ."
                }
            }
            //Hello
            message.contains("hello" )->{
                when(random){
                    0-> "Hello Mastah!"
                    1-> "Hello Senpai"
                    2-> "Hello Sensei"
                    else -> "error"
                }
            }

            //How are you?
            message.contains("kabarmu") -> {
                when (random) {
                    0 -> "baik, terimakasih!"
                    1 -> "lumayan..."
                    2 -> "Sangat baik!"
                    else -> "error"
                }
            }

            //What time is it?
            message.contains("waktu") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Open Google
            message.contains("buka") && message.contains("google")-> {
                OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("cari")-> {
                OPEN_SEARCH
            }

            message.contains("lokasi")-> {
                "Universitas Katolik Darma Cendika\n" +
                        "Jl. Dr. Ir. H. Soekarno No.201 Surabaya\n" +
                        "Kecamatan Sukolilo \n" +
                        "Provinsi Jawa Timur\n" +
                        "Kode Pos:60117\n"
            }

            message.contains("kontak")->{
               "Telepon:031-5946482\n" +
                       "Faximile:031-5939625\n" +
                       "Email:info_ukdc@ukdc.ac.idWebsite:www.ukdc.ac.id \n"
            }

            message.contains("sejarah")->{
                "Tanggal  7 Nopember 1984 sekelompok Sarjana dan Cendekiawan Katolik di Surabaya mendirikan Yayasan Darma Cendika, berkedudukan di Surabaya. Yayasan ini bergerak dalam 3 (tiga) bidang kegiatan, yaitu: \n" +
                        "1.\tBidang Pendidikan;\n" +
                        "2.\tBidang Kesehatan;\n" +
                        "3.\tBidang Sosial\n"
            }

            message.contains("visi") -> {
                "Visi :\n" +
                        "Universitas Katolik Darma Cendika menjadi lembaga pendidikan tinggi yang mempunyai reputasi terbaik di Indonesia pada tahun 2035.\n"
            }

            message.contains("misi") -> {
              "Misi :\n" +
                      "Universitas Katolik Darma Cendika sebagai Universitas Katolik yang berkomitmen mengintegrasikan pengajaran dan penelitian untuk meningkatkan kesejahteraan masyarakat Indonesia.\n"
            }

            message.contains("tujuan") -> {
                "1.\tMenjamin terlaksananya pelayanan pengajaran yang terbaik berdasarkan standar nasional dan internasional pendidikan tinggi.\n" +
                        "2.\tMenerapkan sistem manajemen yang menjamin pelayanan administrasi yang berkualitas.\n" +
                        "3.\tMenanamkan nilai-nilai kepedulian, kejujuran dan tanggungjawab kepada civitas akademika, karyawan dan masyarakat.\n"
            }

            message.contains("ada jurusan apa aja") || message.contains("jurusan")

            -> {
                "Arsitektur(S1),Teknik Industri(S1),Ilmu Informatik(S1),Akupuntur(D4),Akuntansi(S1),Manajemen(S1),Ilmu Hukum(S1)"
            }

            message.contains("ada berapa fakultas") || message.contains("fakultas")
            ->{
                "saat ini ukdc memiliki fakultas teknik,hukum dan ekonomi"
            }

            message.contains("siapa kamu") ->{
                "saya adalah DCTalk sebuah bot untuk membantu anda menjawab terkait Kampus UKDC\n"
            }

            message.contains("info beasiswa") || message.contains("beasiswa") ->{
                INFO_BEASISWA
            }

            message.contains("biaya kuliah") || message.contains("biaya") ->{
                BIAYA_KULIAH
            }
            else -> {
                when(random){
                    0 -> "saya tidak paham.."
                    1 -> "Coba pertanyaan lain"
                    2 -> "Pertanyaan terlalu sulit dibaca"
                    else-> "error"
                }
        }
        }


    }
}