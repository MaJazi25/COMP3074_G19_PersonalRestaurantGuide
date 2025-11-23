package com.example.restaurantguide.model

data class Restaurant(
    val id: String,
    val name: String,
    val address: String,
    val phones: List<String>,
    val description: String,
    val tags: List<String>,
    val rating: Float
)
