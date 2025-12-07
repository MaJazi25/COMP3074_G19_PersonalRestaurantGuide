package com.example.restaurantguide.data

import android.content.Context
import com.example.restaurantguide.model.Restaurant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID

object RestaurantRepository {
    private lateinit var dao: RestaurantDao
    private val items = mutableListOf<Restaurant>()

    fun init(ctx: Context) {
        val db = RestaurantDatabase.getDatabase(ctx)
        dao = db.restaurantDao()
        
        // Load data from database
        runBlocking {
            items.clear()
            items.addAll(dao.getAll())
            
            // Add sample data if empty
            if (items.isEmpty()) {
                val sampleRestaurants = listOf(
                    Restaurant.create(
                        id = UUID.randomUUID().toString(),
                        name = "Blue Maple Bistro",
                        address = "123 Maple St, Toronto, ON M1A 2B3",
                        phonesList = listOf("416-555-0101"),
                        description = "Cozy place for brunch.",
                        tagsList = listOf("coffee", "dessert"),
                        rating = 4.5f
                    ),
                    Restaurant.create(
                        id = UUID.randomUUID().toString(),
                        name = "Sakura Grill",
                        address = "55 Yonge St, Toronto, ON",
                        phonesList = listOf("416-555-0220"),
                        description = "Japanese grill and noodles",
                        tagsList = listOf("japanese", "noodles"),
                        rating = 4.5f
                    )
                )
                sampleRestaurants.forEach { dao.insert(it) }
                items.addAll(sampleRestaurants)
            }
        }
    }

    fun all(): List<Restaurant> = items

    fun find(id: String): Restaurant? = items.find { it.id == id }

    fun add(r: Restaurant) {
        items.add(0, r)
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(r)
        }
    }

    fun update(r: Restaurant) {
        val idx = items.indexOfFirst { it.id == r.id }
        if (idx >= 0) {
            items[idx] = r
            CoroutineScope(Dispatchers.IO).launch {
                dao.update(r)
            }
        }
    }

    fun remove(id: String) {
        items.removeAll { it.id == id }
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteById(id)
        }
    }
}
