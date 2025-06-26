package com.prince.bankr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prince.bankr.data.local.dao.*
import com.prince.bankr.data.local.entities.*
import com.prince.bankr.data.local.converters.Converters

@Database(
    entities = [
        User::class,
        Account::class,
        Category::class,
        Transaction::class,
        BudgetGoal::class,
        Badge::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class BankrDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetGoalDao(): BudgetGoalDao
    abstract fun badgeDao(): BadgeDao
}