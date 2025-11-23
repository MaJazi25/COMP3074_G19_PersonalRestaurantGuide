package com.example.restaurantguide.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantguide.data.RestaurantRepository
import com.example.restaurantguide.databinding.ActivityListBinding
import com.example.restaurantguide.model.Restaurant

class ListActivity : AppCompatActivity() {
    private lateinit var vb: ActivityListBinding
    private lateinit var adapter: RestaurantAdapter
    private val items: MutableList<Restaurant> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        vb = ActivityListBinding.inflate(layoutInflater)
        setContentView(vb.root)

        adapter = RestaurantAdapter(items) { r ->
            startActivity(Intent(this, DetailsActivity::class.java).putExtra("id", r.id))
        }
        vb.recycler.layoutManager = LinearLayoutManager(this)
        vb.recycler.adapter = adapter

        vb.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }
        vb.btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.replaceAll(RestaurantRepository.all())
    }
}
