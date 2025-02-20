package com.example.calorietracker.data

import android.content.Context
import com.example.androidproject.R
import com.example.androidproject.data.FoodItem
import com.opencsv.CSVReader
import java.io.InputStreamReader

class CsvHelper {
    companion object {
        fun readCsv(context: Context): List<FoodItem> {
            val foodItems = mutableListOf<FoodItem>()

            try {
                // Open the CSV file from the raw folder
                val inputStream = context.resources.openRawResource(R.raw.food_data)
                val inputStreamReader = InputStreamReader(inputStream)
                val csvReader = CSVReader(inputStreamReader)
                csvReader.readNext()
                // Read the CSV file line by line
                var nextLine: Array<String>?
                while (csvReader.readNext().also { nextLine = it } != null) {
                    // Skip the header row
                    if (nextLine!![0].equals("Food Name", ignoreCase = true)) {
                        continue
                    }
                    // Extract data from each row
                    val foodName = nextLine!![0]
                    val calories = nextLine!![1].toDoubleOrNull()?.toInt() ?: 0 // Convert to Int, default to 0 if null
                    val carbs = nextLine!![2].toDoubleOrNull() ?: 0.0          // Carbs in grams, default to 0.0 if null
                    val fats = nextLine!![3].toDoubleOrNull() ?: 0.0           // Fats in grams, default to 0.0 if null
                    val protein = nextLine!![4].toDoubleOrNull() ?: 0.0        // Protein in grams, default to 0.0 if null
                    val quantity = nextLine!![5].toIntOrNull() ?: 0            // Quantity, default to 1 if null

                    val foodItem = FoodItem(foodName, calories, protein,carbs,fats, quantity)

                    foodItems.add(foodItem)
                }
                csvReader.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return foodItems
        }
    }
}