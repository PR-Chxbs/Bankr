package com.prince.bankr.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "budget_goals",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["user_id"])
    ]
)
data class BudgetGoal(
    @PrimaryKey( autoGenerate = true) val goal_id: Int = 0,
    val user_id: Int,
    val name: String,
    val description: String,
    val month: Int,
    val year: Int,
    val total_budget: Int
)
