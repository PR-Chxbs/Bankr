package com.prince.bankr.ui.screens.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.bankr.data.local.entities.BudgetGoal
import com.prince.bankr.data.repository.BudgetGoalRepository
import com.prince.bankr.gamification.BadgeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetBudgetViewModel @Inject constructor(
    private val budgetRepo: BudgetGoalRepository,
    private val badgeManager: BadgeManager
) : ViewModel() {

    private val userId = 1 // Replace with session handling logic

    fun saveBudget(name: String, description: String, month: Int, year: Int, totalBudget: Int) {
        viewModelScope.launch {
            val newBudget = BudgetGoal(
                user_id = userId,
                name = name,
                description = description,
                month = month,
                year = year,
                total_budget = totalBudget
            )
            budgetRepo.insertBudgetGoal(newBudget)
            checkForBadges()
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
