package com.example.restaurantguide.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantguide.data.RestaurantRepository
import com.example.restaurantguide.databinding.ActivityAddEditBinding
import com.example.restaurantguide.model.Restaurant
import java.util.UUID

class AddEditActivity : AppCompatActivity() {
    private lateinit var vb: ActivityAddEditBinding
    private var editingId: String? = null
    private val tagChoices = listOf("casual","coffee","family","grill","japanese","noodles","vegan","dessert")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        vb = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.ratingBar.stepSize = 0.5f
        vb.tagsInput.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, tagChoices))

        editingId = intent.getStringExtra("id")
        val existing = editingId?.let { RestaurantRepository.find(it) }
        if (existing != null) {
            vb.etName.setText(existing.name)
            vb.etAddress.setText(existing.address)
            vb.etPhones.setText(existing.phones.joinToString(", "))
            vb.etDescription.setText(existing.description)
            vb.ratingBar.rating = existing.rating
            vb.etTags.setText(existing.tags.joinToString(", "))
        }

        vb.btnSave.setOnClickListener {
            val name = vb.etName.text.toString().trim()
            val address = vb.etAddress.text.toString().trim()
            val phones = vb.etPhones.text.toString()
                .split(",").map { it.trim() }.filter { it.isNotEmpty() }
            val desc = vb.etDescription.text.toString().trim()
            val tags = vb.etTags.text.toString()
                .split(",").map { it.trim() }.filter { it.isNotEmpty() }
            val rating = vb.ratingBar.rating

            if (name.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Name and Address are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val r = Restaurant(
                id = editingId ?: UUID.randomUUID().toString(),
                name = name,
                address = address,
                phones = phones,
                description = desc,
                tags = tags,
                rating = rating
            )
            if (editingId == null) RestaurantRepository.add(r) else RestaurantRepository.update(r)

            setResult(RESULT_OK) // not strictly required if you refresh in onResume
            finish()
        }
    }
}
