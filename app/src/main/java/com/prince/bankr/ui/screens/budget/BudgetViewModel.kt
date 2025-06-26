package com.prince.bankr.ui.screens.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.bankr.data.local.entities.BudgetGoal
import com.prince.bankr.data.local.entities.Transaction
import com.prince.bankr.data.local.enums.Type
import com.prince.bankr.data.local.rich.transaction.TransactionWithDetails
import com.prince.bankr.data.repository.BudgetGoalRepository
import com.prince.bankr.data.repository.TransactionRepository
import com.prince.bankr.gamification.BadgeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val budgetGoalRepo: BudgetGoalRepository,
    private val transactionRepo: TransactionRepository,
    private val badgeManager: BadgeManager
) : ViewModel() {

    private val _userId = 1
    private val currentDate = Calendar.getInstance()
    private val currentMonth = currentDate.get(Calendar.MONTH) + 1
    private val currentYear = currentDate.get(Calendar.YEAR)

    private val _budgetGoal = MutableStateFlow<BudgetGoal?>(null)
    val budgetGoal: StateFlow<BudgetGoal?> = _budgetGoal.asStateFlow()

    private val _expenses = MutableStateFlow<List<TransactionWithDetails>>(emptyList())
    val expenses: StateFlow<List<TransactionWithDetails>> = _expenses.asStateFlow()

    val totalSpent: StateFlow<Int> = _expenses.map { list ->
        list
            .filter { it.transaction.type == Type.EXPENSE }
            .sumOf { it.transaction.amount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val budgetProgress: StateFlow<Float> = combine(
        totalSpent,
        budgetGoal
    ) { spent, goal ->
        if (goal != null && goal.total_budget > 0) {
            spent.toFloat() / goal.total_budget
        } else 0f
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0f)

    val dailyAllowance: StateFlow<Int> = combine(
        totalSpent,
        budgetGoal
    ) { spent, goal ->
        if (goal != null) {
            val today = Calendar.getInstance()
            val daysLeft = today.getActualMaximum(Calendar.DAY_OF_MONTH) - today.get(Calendar.DAY_OF_MONTH) + 1
            val remaining = goal.total_budget - spent
            if (remaining > 0 && daysLeft > 0) remaining / daysLeft else 0
        } else 0
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    init {
        loadBudgetGoal()
        loadExpenses()
    }

    private fun loadBudgetGoal() {
        viewModelScope.launch {
            budgetGoalRepo.getAllBudgetGoals(_userId).collect { goals ->
                _budgetGoal.value = goals.find {
                    it.month == currentMonth && it.year == currentYear
                }
            }
            checkForBadges()
        }
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            transactionRepo.getTransactionsWithDetails(_userId).collect { txns ->
                val filtered = txns.filter {
                    val cal = Calendar.getInstance().apply { time = it.transaction.date }
                    val isSameMonth = cal.get(Calendar.MONTH) + 1 == currentMonth
                    val isSameYear = cal.get(Calendar.YEAR) == currentYear
                    val isExpense = it.transaction.type == Type.EXPENSE
                    isSameMonth && isSameYear && isExpense
                }
                _expenses.value = filtered
                checkForBadges()
            }
        }
    }

    private val _badgeEvents = MutableSharedFlow<String>()
    val badgeEvents = _badgeEvents.asSharedFlow()

    fun checkForBadges() {
        viewModelScope.launch {
            val earned = badgeManager.evaluateBadges(_userId)
            earned.forEach {
                _badgeEvents.emit("🎉 New Badge: ${it.name}!")
            }
        }
    }

}
