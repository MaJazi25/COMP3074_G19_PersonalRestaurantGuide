package com.example.restaurantguide.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantguide.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var vb: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        vb = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        vb.tvAppName.text = "Personal Restaurant Guide\nVersion 1.0"
        vb.tvTeam.text =
            "Android app to save, rate, and\nshare restaurant experiences.\n\n" +
                    "Mehdi Jazi – 101449183\n" +
                    "Adel Alhajhussain – 101532466\n" +
                    "Sueda Eser – 101442502\n\n" +
                    "George Brown College\n" +
                    "COMP3074\n" +
                    "Mobile App Development I"
    }
}
