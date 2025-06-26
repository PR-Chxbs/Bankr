package com.prince.bankr.gamification

import com.prince.bankr.data.repository.BadgeRepository
import com.prince.bankr.data.repository.BudgetGoalRepository
import com.prince.bankr.data.local.entities.Badge
import com.prince.bankr.data.local.enums.Type
import com.prince.bankr.data.repository.TransactionRepository
import kotlinx.coroutines.flow.first
import java.util.*
import javax.inject.Inject

class BadgeManager @Inject constructor(
    private val badgeRepo: BadgeRepository,
    private val budgetGoalRepo: BudgetGoalRepository,
    private val transactionRepo: TransactionRepository
) {
    suspend fun evaluateBadges(userId: Int): List<Badge> {
        val earnedBadges = badgeRepo.getAllBadgesForUser(userId).first()
        val budgetGoals = budgetGoalRepo.getAllBudgetGoals(userId).first()
        val transactions = transactionRepo.getAllTransactionsForUser(userId).first()

        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentYear = calendar.get(Calendar.YEAR)

        val newlyEarned = mutableListOf<Badge>()

        fun hasBadge(name: String) = earnedBadges.any { it.name == name }

        if (!hasBadge("First Budget") && budgetGoals.size == 1) {
            newlyEarned += Badge(0, userId, "First Budget", "You created your first budget!", Date())
        }

        // Check for Expense Crusher
        val expensesThisMonth = transactions.filter {
            it.type == Type.EXPENSE &&
                    Calendar.getInstance().apply { time = it.date }.run {
                        get(Calendar.MONTH) + 1 == currentMonth && get(Calendar.YEAR) == currentYear
                    }
        }
        if (!hasBadge("Expense Crusher") && expensesThisMonth.size >= 20) {
            newlyEarned += Badge(0, userId, "Expense Crusher", "Logged 20+ expenses in one month!", Date())
        }

        // Check for Big Saver
        budgetGoals.find { it.month == currentMonth && it.year == currentYear }?.let { budget ->
            val spent = expensesThisMonth.sumOf { it.amount }
            if (!hasBadge("Big Saver") && budget.total_budget - spent >= 5000) {
                newlyEarned += Badge(0, userId, "Big Saver", "Saved over R5,000 in a month!", Date())
            }
        }

        // Budget Streak (check for last 3 months)
        val streakCount = (0..2).count { offset ->
            val cal = Calendar.getInstance().apply { add(Calendar.MONTH, -offset) }
            val m = cal.get(Calendar.MONTH) + 1
            val y = cal.get(Calendar.YEAR)
            budgetGoals.any { it.month == m && it.year == y }
        }
        if (!hasBadge("Budget Streak") && streakCount == 3) {
            newlyEarned += Badge(0, userId, "Budget Streak", "Created budgets 3 months in a row!", Date())
        }

        // Under Budget
        budgetGoals.find { it.month == currentMonth && it.year == currentYear }?.let { budget ->
            val spent = expensesThisMonth.sumOf { it.amount }
            if (!hasBadge("Under Budget") && spent < budget.total_budget) {
                newlyEarned += Badge(0, userId, "Under Budget", "You stayed under budget this month!", Date())
            }
        }

        // Insert all new badges
        newlyEarned.forEach { badgeRepo.insertBadge(it) }

        return newlyEarned
    }
}
