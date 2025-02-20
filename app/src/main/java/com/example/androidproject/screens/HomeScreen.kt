package com.example.androidproject.screens

import FoodSearchViewModel
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidproject.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(viewModel: FoodSearchViewModel) {
    val macroProgress by viewModel.macroProgress.collectAsState()
    val currentDate = getCurrentDate()
    Log.d("Current Date", currentDate)
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row( modifier = Modifier.fillMaxWidth(), // Add this line,
                 horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Daily Macros Tracker",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF37474F)
                )


                    Text(
                        text = currentDate,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )



            }

            Spacer(modifier = Modifier.height(16.dp))

//            MacroCard(
//                "Calories",
//                "${macroProgress.totalCalories}/2000 kcal",
//                painterResource(id = R.drawable.calorie_macro),
//                color = Color(0xFF37474F),
//                macroProgress.totalCalories.toFloat() / 2000
//            )
//            MacroCard(
//                "Protein",
//                "${macroProgress.protein.toInt()}/150 g",
//                painterResource(id = R.drawable.protein_macro),
//                Color(0xFFFFE28C),
//                macroProgress.protein.toFloat() / 150
//            )
//            MacroCard(
//                "Carbs",
//                "${macroProgress.carbs.toInt()}/175 g",
//                painterResource(id = R.drawable.carbs_macro),
//                Color(0xFF6FABDF),
//                macroProgress.carbs.toFloat() / 175
//            )
//            MacroCard(
//                "Fat",
//                "${macroProgress.fats.toInt()}/78 g",
//                painterResource(id = R.drawable.fat_macro),
//                color =Color(0xFFF3B6C6),
//                macroProgress.fats.toFloat() / 78
//            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()

//                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    MacroCard(
                        "Calories",
                        "${macroProgress.totalCalories}/2000 kcal",
                        painterResource(id = R.drawable.calorie_macro),
                        color = Color(0xFF37474F),
                        macroProgress.totalCalories.toFloat() / 2000
                    )
                }

                item {
                    MacroCard(
                        "Protein",
                        "${macroProgress.protein.toInt()}/150 g",
                        painterResource(id = R.drawable.protein_macro),
                        Color(0xFFFFE28C),
                        macroProgress.protein.toFloat() / 150
                    )
                }

                item {
                    MacroCard(
                        "Carbs",
                        "${macroProgress.carbs.toInt()}/175 g",
                        painterResource(id = R.drawable.carbs_macro),
                        Color(0xFF6FABDF),
                        macroProgress.carbs.toFloat() / 175
                    )
                }

                item {
                    MacroCard(
                        "Fat",
                        "${macroProgress.fats.toInt()}/78 g",
                        painterResource(id = R.drawable.fat_macro),
                        color = Color(0xFFF3B6C6),
                        macroProgress.fats.toFloat() / 78
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentDate():String{
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault()) // Adjust format as needed
    return currentDate.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateString: String): String? { // Return String? to handle errors
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        val date = LocalDate.parse(dateString, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.getDefault())
        date.format(outputFormatter)
    } catch (e: DateTimeParseException) {
        // Handle parsing error (e.g., log, return null, show error message)
        e.printStackTrace()
        null
    }
}

@Composable
fun MacroCard(
    title: String,
    value: String,
    icon: Painter,
    color: Color,
    progress: Float
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {

Image(painter = icon, contentDescription = null,
    modifier = Modifier.size(70.dp))
            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF455A64)
                )
                Text(text = value, fontSize = 14.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(6.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth(),
                    color = color,
                )
            }
        }
    }
}
