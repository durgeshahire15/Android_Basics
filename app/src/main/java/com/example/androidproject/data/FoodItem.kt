package com.example.androidproject.data

data class FoodItem(
    val foodName: String,
    val calories: Int,
    val macroNutrients: Macros, // Nested data class for macros,
    val quantity:Int = 0,
) {
    data class Macros(
        val protein: Double, // Protein in grams
        val carbs: Double,   // Carbs in grams
        val fats: Double     // Fats in grams
    )
}
