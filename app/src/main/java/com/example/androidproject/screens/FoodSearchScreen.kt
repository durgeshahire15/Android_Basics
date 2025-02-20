import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidproject.R
import com.example.androidproject.data.FoodItem

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FoodSearchScreen(viewModel: FoodSearchViewModel) {
    val uiState by viewModel.uiState.collectAsState() // Collect UI state
    val searchQuery by viewModel.searchQuery.collectAsState() // Collect search query

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        OutlinedTextField(value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChanged(it) },
            label = { Text("Search Food") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {  // Use when to handle different UI states
            is FoodSearchViewModel.UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally)) // Centered loading indicator
            }

            is FoodSearchViewModel.UiState.Success -> {
                if (state.foodItems.isEmpty()) {
                    Text(
                        text = "No items found",
                        modifier = Modifier.fillMaxWidth(), // Fill width for centering
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn {
                        items(state.foodItems) { foodItem ->
                            FoodItemRow(foodItem, viewModel)
                        }
                    }
                }
            }

            is FoodSearchViewModel.UiState.Empty -> {
                Text(
                    text = "No items found",
                    modifier = Modifier.fillMaxWidth(), // Fill width for centering
                    textAlign = TextAlign.Center
                )
            }

            else -> {}
        }
    }
}


@Composable
fun FoodItemRow(foodItem: FoodItem, viewModel: FoodSearchViewModel) {
//    val selectedFoodItems by viewModel.uiState.value
//    val isSelected = selectedFoodItems.contains(foodItem)


    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable {
//            viewModel.toggleFoodItemSelection(foodItem)
//            viewModel.updateItemCount(foodItem, foodItem.quantity + 1)
        }
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .border(
            width = 0.75.dp,
            color = if (foodItem.quantity > 0) MaterialTheme.colorScheme.primary else Color.Transparent, // Conditional border color,
            shape = MaterialTheme.shapes.medium
        ),

        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), // Important: Fill the width for SpaceBetween to work
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = foodItem.foodName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row{
                Icon(
                    painter = painterResource(R.drawable.baseline_add_24),
                    contentDescription = stringResource(id = R.string.bus_content_description),
                    modifier = Modifier.clickable { viewModel.updateItemCount(foodItem, foodItem.quantity + 1) }
                )
                Text(
                    text = if (foodItem.quantity == 0) "Quantity" else foodItem.quantity.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    painter = painterResource(R.drawable.baseline_minimize_24),
                    contentDescription = stringResource(id = R.string.minimise_description),
                    modifier = Modifier.clickable { viewModel.updateItemCount(foodItem, foodItem.quantity - 1) }
                )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

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

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MacroNutrientItem("Carbs", foodItem.carbs)
                VerticalDivider(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                MacroNutrientItem("Fats", foodItem.fats)
                VerticalDivider(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                MacroNutrientItem("Protein", foodItem.protein)
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

//@Composable
//private fun VerticalDivider() {
//    Divider(
//        modifier = Modifier
//            .height(24.dp)
//            .width(1.dp),
//        color = MaterialTheme.colorScheme.outlineVariant
//    )
//}

@Preview()
@Composable

fun previewFoodItemRow(
    foodItem: FoodItem = FoodItem(
        foodName = "Avocado Toast", calories = 320,
            carbs = 20.0, fats = 25.0, protein = 5.0

    )
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable {
//            viewModel.toggleFoodItemSelection(foodItem)
        }
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .border(
            width = 0.75.dp,
            color = if (true) MaterialTheme.colorScheme.primary else Color.Transparent, // Conditional border color,
            shape = MaterialTheme.shapes.medium
        ),

        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), // Important: Fill the width for SpaceBetween to work

                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = foodItem.foodName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "ADD+",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.inversePrimary,  // Conditional border color,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(10.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

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

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MacroNutrientItem("Carbs", foodItem.carbs)
                VerticalDivider(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                MacroNutrientItem("Fats", foodItem.fats)
                VerticalDivider(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                MacroNutrientItem("Protein", foodItem.protein)
            }
        }
    }
}
