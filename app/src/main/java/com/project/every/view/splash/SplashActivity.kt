package com.project.every.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.project.every.view.MainActivity
import com.project.every.R

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME : Long = 800

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setSplash()
    }

    private fun setSplash(){
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()}, SPLASH_TIME)
    }
}
