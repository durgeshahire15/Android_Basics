package com.example.androidproject

import FoodSearchScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidproject.data.FoodItem
import com.example.androidproject.screens.HomeScreen
import com.example.androidproject.ui.theme.AndroidProjectTheme
import com.example.calorietracker.data.CsvHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
             MainScreen()
        }
    }
}


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavigationGraph(navController, Modifier.padding(innerPadding))
    }
}
@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar(containerColor = Color.LightGray , tonalElevation = 2.dp,
        ) {
        NavigationBarItem(
            icon = { Text("🏠") },
            label = { Text("Home") },
            selected = false,
            onClick = { navController.navigate("home") }
        )

        NavigationBarItem(
            icon = { Text("🔍") },
            label = { Text("Search") },
            selected = false,
            onClick = { navController.navigate("search") }
        )
    }
}



@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController, startDestination = "home", modifier = modifier) {
        composable("home") { HomeScreen() }
        composable("search") { FoodSearchScreen() }
    }
}

@Preview(showBackground = true,)
@Composable
fun PreviewBottomNavBar() {
    NavigationBar(containerColor = Color.LightGray , tonalElevation = 4.dp,
        ) {
        NavigationBarItem(
            icon = { Text("🏠") },
            label = { Text("Home") },
            selected = false,
            onClick = {
//                navController.navigate("search")
            }
        )

        NavigationBarItem(
            icon = { Text("🔍") },
            label = { Text("Search") },
            selected = false,
            onClick = {
//                navController.navigate("search")
            }
        )
    }
}