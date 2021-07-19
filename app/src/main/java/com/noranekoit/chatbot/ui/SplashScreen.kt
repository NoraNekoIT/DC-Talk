package com.noranekoit.chatbot.ui

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.noranekoit.chatbot.R
import kotlinx.android.synthetic.main.activity_petunjuk.*


class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIME :Long =3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val versionApk : TextView =findViewById(R.id.tv_version)
        val pm = applicationContext.packageManager
        val pkgName = applicationContext.packageName
        var pkgInfo: PackageInfo? = null
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0)
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }
        val ver = pkgInfo!!.versionName
        versionApk.setText("DC TALK Version" + ver)

        Handler().postDelayed(
            {
                startActivity(Intent(this, Home::class.java))
                finish()
            },SPLASH_TIME
        )
    }
}