package com.prince.bankr.data.repository

import kotlinx.coroutines.flow.Flow
import com.prince.bankr.data.local.entities.BudgetGoal
import com.prince.bankr.data.local.dao.BudgetGoalDao

class BudgetGoalRepository (private val budgetGoalDao: BudgetGoalDao) {

    suspend fun insertBudgetGoal(budgetGoal: BudgetGoal) {
        budgetGoalDao.insertBudgetGoal(budgetGoal)
    }

    suspend fun updateBudgetGoal(budgetGoal: BudgetGoal) {
        budgetGoalDao.updateBudgetGoal(budgetGoal)
    }

    suspend fun deleteBudgetGoal(budgetGoal: BudgetGoal) {
        budgetGoalDao.deleteBudgetGoal(budgetGoal)
    }

    suspend fun getBudgetGoalById(userId: Int, goalId: Int): BudgetGoal {
        return budgetGoalDao.getBudgetGoalById(userId, goalId)
    }

    fun getAllBudgetGoals(userId: Int): Flow<List<BudgetGoal>> {
        return budgetGoalDao.getAllBudgetGoals(userId)
    }
}