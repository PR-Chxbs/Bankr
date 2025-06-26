package com.prince.bankr.ui.screens.addTransaction

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.bankr.data.local.entities.Category
import com.prince.bankr.data.local.entities.Transaction
import com.prince.bankr.data.local.enums.Type
import com.prince.bankr.data.repository.*
import com.prince.bankr.gamification.BadgeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionRepo: TransactionRepository,
    private val accountRepo: AccountRepository,
    private val categoryRepo: CategoryRepository,
    private val badgeManager: BadgeManager
): ViewModel() {

    // UI State
    var selectedAccountId by mutableStateOf<Int?>(null)
    var selectedCategoryId by mutableStateOf<Int?>(null)
    var amount by mutableStateOf(0)
    var description by mutableStateOf("")
    var date by mutableStateOf(Date())
    var receiptImagePath by mutableStateOf<String?>(null)
    var transactionType by mutableStateOf(Type.EXPENSE)

    // For dropdowns
    val accounts = accountRepo.getAccountsForUser(userId = 1)
    val categories = mutableStateOf<List<Category>>(emptyList())

    // Session vars
    val userId: Int = 1

    var imageUri: Uri? by mutableStateOf(null)

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepo.getAllCategories(userId = 1).collect { categoryList ->
                categories.value = categoryList
            }
        }
    }

    fun addTransaction() {
        val transaction = Transaction(
            user_id = userId,
            account_id = selectedAccountId ?: return,
            category_id = selectedCategoryId ?: return,
            amount = amount,
            date = date,
            description = description,
            type = transactionType,
            receipt_image_path = receiptImagePath
        )

        viewModelScope.launch {
            transactionRepo.insertTransaction(transaction)
        }
    }

    private val _badgeEvents = MutableSharedFlow<String>()
    val badgeEvents = _badgeEvents.asSharedFlow()

    fun checkForBadges() {
        viewModelScope.launch {
            val earned = badgeManager.evaluateBadges(userId)
            earned.forEach {
                _badgeEvents.emit("🎉 New Badge: ${it.name}!")
            }
        }
    }
}