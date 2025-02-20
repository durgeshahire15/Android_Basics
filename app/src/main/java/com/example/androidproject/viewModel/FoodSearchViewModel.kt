import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidproject.data.FoodItem
import com.example.androidproject.data.FoodItemDao
import com.example.calorietracker.data.CsvHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FoodSearchViewModel(application: Application, private val foodItemDao: FoodItemDao? = null) : AndroidViewModel(application) {

    private val _allFoodItems = MutableStateFlow<List<FoodItem>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    private val _macroProgress = MutableStateFlow(MacroProgress())

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    val macroProgress: StateFlow<MacroProgress> = _macroProgress.asStateFlow()

    data class MacroProgress(
        var totalCalories: Int = 0,
        var protein: Double = 0.0,
        var carbs: Double = 0.0,
        var fats: Double = 0.0
    )

    // Update FoodItem to include quantity

    sealed class UiState {
        object Loading : UiState()
        data class Success(val foodItems: List<FoodItem>) : UiState()
        object Empty : UiState()
    }

    init {
        viewModelScope.launch {
            val foodItems = CsvHelper.readCsv(application.applicationContext)
            _allFoodItems.value = foodItems

            _uiState.value = if (foodItems.isEmpty()) UiState.Empty else UiState.Success(foodItems)
        }

        // Search query handling
        viewModelScope.launch {
            combine(_searchQuery, _allFoodItems) { query, items ->
                Pair(query, items)
            }
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { (query, items) ->
                    filterFoodItemsFlow(query, items)
                }
                .collect { filteredItems ->
                    _uiState.value = when {
                        filteredItems.isEmpty() && _searchQuery.value.isNotEmpty() -> UiState.Empty
                        else -> UiState.Success(filteredItems)
                    }
                }
        }

        // Observe allFoodItems for quantity changes and update macros
        viewModelScope.launch {
            _allFoodItems
                .map { items -> items.filter { it.quantity > 0 } }
                .collect { itemsWithQuantity ->
                    updateMacroProgress(itemsWithQuantity)
                }
        }
    }

    fun updateItemCount(foodItem: FoodItem, newQuantity: Int) {
        val currentItems = _allFoodItems.value.toMutableList()
        val itemIndex = currentItems.indexOfFirst { it.foodName == foodItem.foodName }

        if (itemIndex != -1 && newQuantity>=0) {
            // Create new item with updated quantity
            val updatedItem = foodItem.copy(quantity = newQuantity)
            currentItems[itemIndex] = updatedItem
            _allFoodItems.value = currentItems
        }
    }

    private fun filterFoodItemsFlow(query: String, items: List<FoodItem>): Flow<List<FoodItem>> {
        return if (query.isEmpty()) {
            flowOf(items)
        } else {
            flowOf(items.filter { it.foodName.contains(query, ignoreCase = true) })
        }
    }

    private fun updateMacroProgress(items: List<FoodItem>) {
        val progress = MacroProgress()
        items.forEach { foodItem ->
            // Multiply macros by quantity
            progress.totalCalories += foodItem.calories * foodItem.quantity
            progress.protein += foodItem.protein * foodItem.quantity
            progress.carbs += foodItem.carbs * foodItem.quantity
            progress.fats += foodItem.fats * foodItem.quantity
        }
        _macroProgress.value = progress
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}
