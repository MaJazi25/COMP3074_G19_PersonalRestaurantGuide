package com.example.restaurantguide

import android.app.Application
import com.example.restaurantguide.data.RestaurantRepository

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        RestaurantRepository.init(this)
    }
}
