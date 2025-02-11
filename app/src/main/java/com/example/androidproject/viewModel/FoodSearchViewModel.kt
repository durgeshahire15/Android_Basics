import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidproject.data.FoodItem
import com.example.calorietracker.data.CsvHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FoodSearchViewModel(application: Application) : AndroidViewModel(application) {

    private val _allFoodItems = MutableStateFlow<List<FoodItem>>(emptyList())
    private val _searchQuery = MutableStateFlow("")
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    private val _selectedFoodItems = MutableStateFlow<Set<FoodItem>>(mutableSetOf()) // Use a Set for efficiency


    private val _macroProgress =  MutableStateFlow(MacroProgress())

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    val selectedFoodItems: StateFlow<Set<FoodItem>> = _selectedFoodItems.asStateFlow()
    val macroProgress: StateFlow<MacroProgress> = _macroProgress.asStateFlow() // Expose macro progress

    data class MacroProgress(
        var totalCalories: Int = 0,
        var protein: Double = 0.0,
        var carbs: Double = 0.0,
        var fats: Double = 0.0
    )

    sealed class UiState {
        object Loading : UiState()
        data class Success(val foodItems: List<FoodItem>) : UiState()
        object Empty : UiState()
    }

    init {
        // Load initial data
        viewModelScope.launch {
            val foodItems = CsvHelper.readCsv(application.applicationContext)
            _allFoodItems.value = foodItems

            // Set initial UI state
            _uiState.value = if (foodItems.isEmpty()) UiState.Empty else UiState.Success(foodItems)
        }

        // Observe search query and filter items
        viewModelScope.launch {
            combine(_searchQuery, _allFoodItems) { query, items -> // Combine FIRST
                Pair(query, items) // Emit a Pair of query and items
            }
                .debounce(300)
                .distinctUntilChanged()
                .flatMapLatest { (query, items) -> // Destructure the Pair
                    filterFoodItemsFlow(query, items) // Pass both to the filter function
                }
                .collect { filteredItems ->
                    _uiState.value = when {
                        filteredItems.isEmpty() && _searchQuery.value.isNotEmpty() -> UiState.Empty
                        else -> UiState.Success(filteredItems)
                    }
                }
        }

        // Update macro progress whenever selected items change
        viewModelScope.launch {
            _selectedFoodItems.collect {
                updateMacroProgress()
            }
        }

    }

    fun toggleFoodItemSelection(foodItem: FoodItem) {
        val currentItems = _selectedFoodItems.value.toMutableSet() // Use a MutableSet

        if (currentItems.contains(foodItem)) {
            currentItems.remove(foodItem)
        } else {
            currentItems.add(foodItem)
        }
        _selectedFoodItems.value = currentItems.toSet() // Update with an immutable Set
    }

    fun updateItemCount(foodItem:FoodItem){

    }

    private fun filterFoodItemsFlow(query: String, items: List<FoodItem>): Flow<List<FoodItem>> {
        return if (query.isEmpty()) {
            flowOf(items) // Emit the items directly as a Flow
        } else {
            flowOf(items.filter { it.foodName.startsWith(query, ignoreCase = true) })
        }
    }
    private fun updateMacroProgress() {
        val progress = MacroProgress()
        _selectedFoodItems.value.forEach { foodItem ->
            progress.totalCalories += foodItem.calories
            progress.protein += foodItem.macroNutrients.protein
            progress.carbs += foodItem.macroNutrients.carbs
            progress.fats += foodItem.macroNutrients.fats
        }
        _macroProgress.value = progress
    }
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}