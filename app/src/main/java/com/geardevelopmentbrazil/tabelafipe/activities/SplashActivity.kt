package com.geardevelopmentbrazil.tabelafipe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geardevelopmentbrazil.tabelafipe.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intent : Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}