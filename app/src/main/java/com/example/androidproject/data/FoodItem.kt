package com.example.androidproject.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_items")
data class FoodItem(
    @PrimaryKey val foodName: String,
    val calories: Int,
    val protein: Double, // Protein in grams
    val carbs: Double,   // Carbs in grams
    val fats: Double,   // Fats in grams
    val quantity: Int = 0
)
