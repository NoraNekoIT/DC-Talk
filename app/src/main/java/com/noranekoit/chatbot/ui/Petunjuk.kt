package com.noranekoit.chatbot.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.appcompat.app.ActionBar
import com.noranekoit.chatbot.R
import kotlinx.android.synthetic.main.activity_floating_button.*
import kotlinx.android.synthetic.main.activity_petunjuk.*

class Petunjuk : AppCompatActivity() {

    private var title: String = "Info"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petunjuk)

        setActionBarTitle(title)

        tv_petunjuk.text =
                " hitung angka * atau + atau/atau- angka\n" +
                "\n" +
                " hello\n" +
                "\n" +
                " kabarmu\n" +
                "\n" +
                " waktu ?\n" +
                "\n" +
                " buka google\n" +
                "\n" +
                " cari\n" +
                "\n" +
                " lokasi\n" +
                "\n" +
                " kontak\n" +
                "\n" +
                " sejarah\n" +
                "\n" +
                " visi \n" +
                "\n" +
                " misi\n" +
                "\n" +
                " tujuan\n" +
                "\n" +
                " ada jurusan apa aja atau prodi atau fakultas\n" +
                "\n" +
                " ada berapa fakultas atau fakultas\n" +
                "\n" +
                " siapa kamu\n" +
                "\n" +
                " info beasiswa atau beasiswa\n" +
                "\n" +
                " biaya kuliah atau biaya"
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = title
        }
    }




}