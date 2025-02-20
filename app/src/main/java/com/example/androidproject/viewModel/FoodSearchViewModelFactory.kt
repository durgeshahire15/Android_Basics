package com.example.androidproject.viewModel
import FoodSearchViewModel
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidproject.data.FoodItemDao

class FoodSearchViewModelFactory(private val application: Application, private val foodItemDao: FoodItemDao? = null  ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodSearchViewModel(application, foodItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}