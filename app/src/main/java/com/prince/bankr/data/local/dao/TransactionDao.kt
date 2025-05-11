package com.prince.bankr.data.local.dao

import androidx.room.*
import com.prince.bankr.data.local.entities.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Update
    suspend fun updateTransaction(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

    @Query("SELECT * FROM transactions WHERE user_id = :userId ORDER BY date DESC")
    fun getAllTransactionsForUser(userId: Int): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE account_id = :accountId ORDER BY date DESC")
    fun getTransactionsForAccount(accountId: Int): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE category_id = :categoryId ORDER BY date DESC")
    fun getTransactionsForCategory(categoryId: Int): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE transaction_id = :transactionId")
    suspend fun getTransactionById(transactionId: Int): Transaction?

    @Query("SELECT SUM(amount) FROM transactions WHERE user_id = :userId AND type = :type")
    suspend fun getTotalAmountByType(userId: Int, type: String): Int?
}