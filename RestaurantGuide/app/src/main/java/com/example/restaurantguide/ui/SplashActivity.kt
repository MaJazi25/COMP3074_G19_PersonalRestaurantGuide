package com.example.restaurantguide.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantguide.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var vb: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        vb = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(vb.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, ListActivity::class.java))
            finish()
        }, 5000)
    }
}
