import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidproject.data.FoodItem
import com.example.androidproject.viewModel.FoodSearchViewModelFactory

@Composable
fun FoodSearchScreen() {
    val context = LocalContext.current.applicationContext as Application

    // ✅ Correct way to initialize ViewModel
    val viewModel: FoodSearchViewModel = viewModel(
        factory = FoodSearchViewModelFactory(context)
    )

    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredFoodItems by viewModel.filteredFoodItems.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChanged(it) },
            label = { Text("Search Food") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        if(filteredFoodItems.isEmpty()){
            Text(text = "No items found")
        }
        else{
        LazyColumn {
            items(filteredFoodItems) { foodItem ->
                FoodItemRow(foodItem)
            }
        }
    }
    }
}

//@Composable
//fun FoodItemRow(foodItem: FoodItem) {
//    Card(
//        modifier = Modifier.fillMaxWidth().padding(8.dp),
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = foodItem.foodName, style = MaterialTheme.typography.bodyMedium)
//            Text(text = "Calories: ${foodItem.calories}")
//            Text(text = "Carbs: ${foodItem.macroNutrients.carbs}g | Fats: ${foodItem.macroNutrients.fats}g | Protein: ${foodItem.macroNutrients.protein}g")
//        }
//    }
//}
@Composable
fun FoodItemRow(foodItem: FoodItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            // Food Name with larger, bold typography
            Text(
                text = foodItem.foodName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Calories with emphasis
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${foodItem.calories}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = " calories",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Macronutrients in a Row with dividers
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MacroNutrientItem("Carbs", foodItem.macroNutrients.carbs)
                VerticalDivider()
                MacroNutrientItem("Fats", foodItem.macroNutrients.fats)
                VerticalDivider()
                MacroNutrientItem("Protein", foodItem.macroNutrients.protein)
            }
        }
    }
}

@Composable
private fun MacroNutrientItem(label: String, value: Double) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${value}g",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun VerticalDivider() {
    Divider(
        modifier = Modifier
            .height(24.dp)
            .width(1.dp),
        color = MaterialTheme.colorScheme.outlineVariant
    )
}


//@Composable
//fun FoodItemRow(foodItem: FoodItem) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp),
//        shape = RoundedCornerShape(12.dp), // Rounded corners
//        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp), // Adds shadow
//        colors = CardDefaults.cardColors(containerColor = Color.White)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // 🥗 Circular Icon Placeholder (Can Replace with Image)
//            Box(
//                modifier = Modifier
//                    .size(50.dp)
//                    .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(50)) // Light blue background
//            )
//
//            Spacer(modifier = Modifier.width(12.dp))
//
//            // 📌 Food Info (Name + Nutrition)
//            Column {
//                Text(
//                    text = foodItem.foodName,
//                    style = MaterialTheme.typography.titleMedium,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp
//                )
//
//                Text(
//                    text = "Calories: ${foodItem.calories}",
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontSize = 14.sp,
//                    color = Color.Gray
//                )
//
//                Text(
//                    text = "Carbs: ${foodItem.macroNutrients.carbs}g | Fats: ${foodItem.macroNutrients.fats}g | Protein: ${foodItem.macroNutrients.protein}g",
//                    style = MaterialTheme.typography.bodySmall,
//                    fontSize = 13.sp,
//                    color = Color.DarkGray
//                )
//            }
//        }
//    }
//}
//
@Preview(showBackground = true)
@Composable
fun PreviewFoodItemRow() {
    MaterialTheme {
        FoodItemRow(
            FoodItem(
                foodName = "Avocado Toast",
                calories = 250,
                macroNutrients = FoodItem.Macros(protein = 4.0, carbs = 24.0, fats = 14.0),
                quantity = 1
            )
        )
    }
}

//@Composable
//fun FoodItemRow(foodItem: FoodItem) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .shadow(8.dp, RoundedCornerShape(16.dp)), // Soft shadow for depth
//        shape = RoundedCornerShape(16.dp), // Classy rounded corners
//        colors = CardDefaults.cardColors(containerColor = Color.Transparent) // Transparent card
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .background(
//                    brush = Brush.verticalGradient( // Elegant gradient effect
//                        colors = listOf(Color(0xFFF8F9FA), Color(0xFFE3F2FD))
//                    ),
//                    shape = RoundedCornerShape(16.dp)
//                )
//                .padding(16.dp)
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                // 🌟 Circular Image Placeholder (Can be replaced with actual food image)
//                Box(
//                    modifier = Modifier
//                        .size(60.dp)
//                        .background(Color(0xFFD1C4E9), shape = CircleShape) // Soft purple
//                        .shadow(4.dp, CircleShape) // Subtle shadow
//                )
//
//                Spacer(modifier = Modifier.width(12.dp))
//
//                // 📌 Food Info
//                Column {
//                    Text(
//                        text = foodItem.foodName,
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 20.sp,
//                        color = Color(0xFF37474F) // Deep gray-blue
//                    )
//
//                    Text(
//                        text = "Calories: ${foodItem.calories}",
//                        style = MaterialTheme.typography.bodyMedium,
//                        fontSize = 14.sp,
//                        color = Color(0xFF616161) // Soft gray
//                    )
//
//                    Text(
//                        text = "Carbs: ${foodItem.macroNutrients.carbs}g | Fats: ${foodItem.macroNutrients.fats}g | Protein: ${foodItem.macroNutrients.protein}g",
//                        style = MaterialTheme.typography.bodySmall,
//                        fontSize = 13.sp,
//                        color = Color(0xFF78909C) // Elegant bluish-gray
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewFoodItemRow() {
//    MaterialTheme {
//        FoodItemRow(
//            FoodItem(
//                foodName = "Avocado Toast",
//                calories = 250,
//                macroNutrients = FoodItem.Macros(protein = 4.0, carbs = 24.0, fats = 14.0),
//                quantity = 1
//            )
//        )
//    }
//}