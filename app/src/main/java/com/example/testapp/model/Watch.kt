package com.example.testapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Watch(
    val id: String,
    val brand: String,
    val model: String,
    val price: Int,
    val imageUrl: String,
    val description: String,
    val movement: String,
    val waterResistance: Int,
    val caseMaterial: String,
    val strapMaterial: String,
    val releaseYear: Int,
    val dialColor: String,
    val caseDiameter: Double,
    val features: List<String>,
    val stock: Int,
    val isAvailable: Boolean
)