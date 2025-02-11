package com.example.androidproject

import FoodSearchScreen
import FoodSearchViewModel
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidproject.screens.HomeScreen
import com.example.androidproject.viewModel.FoodSearchViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
             MainScreen()
        }
    }
}


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext as Application

    // Create shared ViewModel with custom factory
    val sharedViewModel: FoodSearchViewModel = viewModel(
        factory = FoodSearchViewModelFactory(context)
    )
    Scaffold(
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->
        NavigationGraph(navController = navController, modifier = Modifier.padding(innerPadding), sharedViewModel =  sharedViewModel)
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    Box(
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            tonalElevation = 2.dp,
        ) {
            NavigationBarItem(
                icon = { Text("🏠") },
                label = { Text("Home") },
                selected = false, // Set to true for the selected item
                onClick = { navController.navigate("home") }
            )

            NavigationBarItem(
                icon = { Text("🔍") },
                label = { Text("Search") },
                selected = false, // Set to true for the selected item
                onClick = { navController.navigate("search") }
            )
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier, sharedViewModel: FoodSearchViewModel ) {
    NavHost(navController, startDestination = "home", modifier = modifier) {
        composable("home") { HomeScreen(viewModel = sharedViewModel
        ) }
        composable("search") { FoodSearchScreen(viewModel = sharedViewModel  // Pass the shared ViewModel
        ) }
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