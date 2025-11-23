package com.example.restaurantguide.data

import android.content.Context
import android.content.SharedPreferences
import com.example.restaurantguide.model.Restaurant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

object RestaurantRepository {
    private const val PREFS = "restaurants_prefs"
    private const val KEY = "restaurants_json"

    private lateinit var prefs: SharedPreferences
    private val gson = Gson()
    private val items = mutableListOf<Restaurant>()

    fun init(ctx: Context) {
        prefs = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        load()
        if (items.isEmpty()) {
            items.addAll(
                listOf(
                    Restaurant(
                        id = UUID.randomUUID().toString(),
                        name = "Blue Maple Bistro",
                        address = "123 Maple St, Toronto, ON M1A 2B3",
                        phones = listOf("416-555-0101"),
                        description = "Cozy place for brunch.",
                        tags = listOf("coffee", "dessert"),
                        rating = 4.5f
                    ),
                    Restaurant(
                        id = UUID.randomUUID().toString(),
                        name = "Sakura Grill",
                        address = "55 Yonge St, Toronto, ON",
                        phones = listOf("416-555-0220"),
                        description = "Japanese grill and noodles",
                        tags = listOf("japanese", "noodles"),
                        rating = 4.5f
                    )
                )
            )
            save()
        }
    }

    fun all(): List<Restaurant> = items

    fun find(id: String): Restaurant? = items.find { it.id == id }

    fun add(r: Restaurant) {
        items.add(0, r)
        save()
    }

    fun update(r: Restaurant) {
        val idx = items.indexOfFirst { it.id == r.id }
        if (idx >= 0) {
            items[idx] = r
            save()
        }
    }

    fun remove(id: String) {
        items.removeAll { it.id == id }
        save()
    }

    private fun load() {
        val json = prefs.getString(KEY, null) ?: return
        val type = object : TypeToken<List<Restaurant>>() {}.type
        val loaded: List<Restaurant> = gson.fromJson(json, type) ?: emptyList()
        items.clear()
        items.addAll(loaded)
    }

    private fun save() {
        val json = gson.toJson(items)
        prefs.edit().putString(KEY, json).apply()
    }
}
