package com.prince.bankr.data.local.dao

import androidx.room.*
import com.prince.bankr.data.local.entities.BudgetGoal
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetGoalDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudgetGoal(budgetGoal: BudgetGoal)

    @Update
    suspend fun updateBudgetGoal(budgetGoal: BudgetGoal)

    @Delete
    suspend fun deleteBudgetGoal(budgetGoal: BudgetGoal)

    @Query("SELECT * FROM budget_goals WHERE user_id = :userId AND goal_id = :goalId")
    suspend fun getBudgetGoalById(userId: Int, goalId: Int): BudgetGoal

    @Query("SELECT * FROM budget_goals WHERE user_id = :userId")
    fun getAllBudgetGoals(userId: Int): Flow<List<BudgetGoal>>
}