package com.example.restaurantguide.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "restaurants")
data class Restaurant(
    @PrimaryKey
    val id: String,
    val name: String,
    val address: String,
    val phones: String, // Stored as JSON string
    val description: String,
    val tags: String, // Stored as JSON string
    val rating: Float
) {
    // Helper methods to convert between List and String
    fun getPhonesList(): List<String> {
        return if (phones.isEmpty()) emptyList()
        else Gson().fromJson(phones, object : TypeToken<List<String>>() {}.type)
    }

    fun getTagsList(): List<String> {
        return if (tags.isEmpty()) emptyList()
        else Gson().fromJson(tags, object : TypeToken<List<String>>() {}.type)
    }

    companion object {
        fun create(
            id: String,
            name: String,
            address: String,
            phonesList: List<String>,
            description: String,
            tagsList: List<String>,
            rating: Float
        ): Restaurant {
            return Restaurant(
                id = id,
                name = name,
                address = address,
                phones = Gson().toJson(phonesList),
                description = description,
                tags = Gson().toJson(tagsList),
                rating = rating
            )
        }
    }
}
