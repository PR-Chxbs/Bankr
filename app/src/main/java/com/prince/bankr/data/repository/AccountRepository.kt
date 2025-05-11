package com.prince.bankr.data.repository

import com.prince.bankr.data.local.entities.Account
import com.prince.bankr.data.local.dao.AccountDao
import kotlinx.coroutines.flow.Flow

class AccountRepository (private val accountDao: AccountDao) {

    suspend fun insertAccount(account: Account) {
        accountDao.insertAccount(account)
    }

    suspend fun updateAccount(account: Account) {
        accountDao.updateAccount(account)
    }

    suspend fun deleteAccount(account: Account) {
        accountDao.deleteAccount(account)
    }

    fun getAccountsForUser(userId: Int): Flow<List<Account>> {
        return accountDao.getAccountsForUser(userId)
    }

    fun getAccountsByBalance(userId: Int, balance:Int): Flow<List<Account>> {
        return accountDao.getAccountsByBalance(userId, balance)
    }

    suspend fun getTotalUserBalance(userId: Int): Int {
        return accountDao.getTotalUserBalance(userId) ?: 0
    }

    suspend fun getTotalUserBalanceByType(userId: Int, type: String): Int {
        return accountDao.getTotalUserBalanceByType(userId, type) ?: 0
    }
}