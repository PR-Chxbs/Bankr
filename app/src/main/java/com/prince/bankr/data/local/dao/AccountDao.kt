package com.prince.bankr.data.local.dao

import androidx.room.*
import com.prince.bankr.data.local.entities.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: Account)

    @Update
    suspend fun updateAccount(account: Account)

    @Delete
    suspend fun deleteAccount(account: Account)

    @Query("SELECT * FROM accounts WHERE user_id = :userId")
    fun getAccountsForUser(userId: Int): Flow<List<Account>>

    @Query("SELECT * FROM accounts WHERE user_id = :userId AND balance > :balance")
    fun getAccountsByBalance(userId: Int, balance:Int): Flow<List<Account>>

    @Query("SELECT SUM(balance) FROM accounts WHERE user_id = :userId")
    suspend fun getTotalUserBalance(userId: Int): Int?

    @Query("SELECT SUM(balance) FROM accounts WHERE user_id = :userId AND type = :type")
    suspend fun getTotalUserBalanceByType(userId: Int, type: String): Int?
}