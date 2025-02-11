import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidproject.data.FoodItem
import com.example.androidproject.viewModel.FoodSearchViewModelFactory
import kotlinx.coroutines.flow.SharingStarted

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FoodSearchScreen(viewModel: FoodSearchViewModel) {
    val uiState by viewModel.uiState.collectAsState() // Collect UI state
    val searchQuery by viewModel.searchQuery.collectAsState() // Collect search query

    Column(

                modifier = Modifier
                .fillMaxSize()
            .padding(top = 16.dp,start = 16.dp, end = 16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
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
    val selectedFoodItems by viewModel.selectedFoodItems.collectAsState()
    val isSelected = selectedFoodItems.contains(foodItem)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.toggleFoodItemSelection(foodItem)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = {viewModel.toggleFoodItemSelection(foodItem)}
        )

        Card(  // Your existing Card composable
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
                Text(
                    text = foodItem.foodName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

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
    HorizontalDivider(
        modifier = Modifier
            .height(24.dp)
            .width(1.dp),
        color = MaterialTheme.colorScheme.outlineVariant
    )
}
