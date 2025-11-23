package com.example.restaurantguide.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantguide.data.RestaurantRepository
import com.example.restaurantguide.databinding.ActivityDetailsBinding
import com.example.restaurantguide.model.Restaurant

class DetailsActivity : AppCompatActivity() {
    private lateinit var vb: ActivityDetailsBinding
    private lateinit var r: Restaurant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        vb = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val id = intent.getStringExtra("id")
        r = id?.let { RestaurantRepository.find(it) }
            ?: run {
                Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show()
                finish()
                return
            }

        vb.tvName.text = r.name
        vb.ratingBar.rating = r.rating
        vb.tvAddress.text = r.address
        vb.tvPhone.text = r.phones.firstOrNull().orEmpty()
        vb.tvTags.text = r.tags.joinToString(", ")
        vb.tvDescription.text = r.description

        vb.btnDirections.setOnClickListener {
            val uri = Uri.parse("google.navigation:q=${Uri.encode(r.address)}")
            val i = Intent(Intent.ACTION_VIEW, uri).setPackage("com.google.android.apps.maps")
            if (i.resolveActivity(packageManager) != null) startActivity(i)
            else startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        vb.btnMap.setOnClickListener {
            val uri = Uri.parse("geo:0,0?q=${Uri.encode(r.address)}(${Uri.encode(r.name)})")
            val i = Intent(Intent.ACTION_VIEW, uri).setPackage("com.google.android.apps.maps")
            if (i.resolveActivity(packageManager) != null) startActivity(i)
            else startActivity(Intent(Intent.ACTION_VIEW, uri))
        }

        vb.btnShare.setOnClickListener {
            val text = buildString {
                appendLine(r.name)
                appendLine(r.address)
                if (r.phones.isNotEmpty()) appendLine(r.phones.joinToString())
                if (r.tags.isNotEmpty()) appendLine(r.tags.joinToString(", "))
            }
            startActivity(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            })
        }

        vb.btnEdit.setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java).putExtra("id", r.id))
        }

        vb.btnDelete.setOnClickListener {
            RestaurantRepository.remove(r.id)
            setResult(RESULT_OK)
            finish()
        }
    }
}
