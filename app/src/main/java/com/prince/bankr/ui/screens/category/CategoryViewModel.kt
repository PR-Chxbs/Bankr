package com.prince.bankr.ui.screens.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf

import com.prince.bankr.data.local.entities.Category
import com.prince.bankr.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepo: CategoryRepository
): ViewModel() {

    private val userId = 1 // hardcoded for now, replace with actual user session

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepo.getAllCategories(userId)
                .collect { list ->
                    _categories.value = list
                }
        }
    }

    suspend fun addCategory(name: String, monthlyLimit: Int?, iconKey: String) {
        val newCategory = Category(
            user_id = userId,
            name = name,
            monthly_limit = monthlyLimit,
            iconKey = iconKey
        )
        categoryRepo.insertCategory(newCategory)
        loadCategories() // refresh after adding
    }
}
