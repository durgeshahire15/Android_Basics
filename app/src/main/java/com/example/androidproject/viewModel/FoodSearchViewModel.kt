import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidproject.data.FoodItem
import com.example.calorietracker.data.CsvHelper


import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FoodSearchViewModel(application: Application) : AndroidViewModel(application) {

    private val _allFoodItems = MutableStateFlow<List<FoodItem>>(emptyList())
    val allFoodItems: StateFlow<List<FoodItem>> = _allFoodItems.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filteredFoodItems = MutableStateFlow<List<FoodItem>>(emptyList())
    val filteredFoodItems: StateFlow<List<FoodItem>> = _filteredFoodItems.asStateFlow()

    init {
        viewModelScope.launch {
            val foodItems = CsvHelper.readCsv(application.applicationContext)
            _allFoodItems.value = foodItems
            _filteredFoodItems.value = foodItems
        }

        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    _filteredFoodItems.value = if (query.isEmpty()) {
                        _allFoodItems.value
                    } else {
                        _allFoodItems.value.filter {
                            it.foodName.startsWith(query, ignoreCase = true)
                        }
                    }
                }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}
