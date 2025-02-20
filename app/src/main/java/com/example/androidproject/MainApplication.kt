package com.example.androidproject
import android.app.Application
import com.example.androidproject.data.AppDatabase

class MainApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}