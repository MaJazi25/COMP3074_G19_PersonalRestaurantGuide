package com.example.restaurantguide.data

import androidx.room.*
import com.example.restaurantguide.model.Restaurant

@Dao
interface RestaurantDao {
    @Query("SELECT * FROM restaurants ORDER BY name ASC")
    suspend fun getAll(): List<Restaurant>

    @Query("SELECT * FROM restaurants WHERE id = :id")
    suspend fun getById(id: String): Restaurant?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(restaurant: Restaurant)

    @Update
    suspend fun update(restaurant: Restaurant)

    @Query("DELETE FROM restaurants WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM restaurants")
    suspend fun deleteAll()
}
