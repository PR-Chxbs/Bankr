package com.prince.bankr.data.repository

import kotlinx.coroutines.flow.Flow
import com.prince.bankr.data.local.dao.TransactionDao
import com.prince.bankr.data.local.entities.Transaction
import com.prince.bankr.data.local.rich.transaction.TransactionWithDetails

class TransactionRepository (private val transactionDao: TransactionDao) {

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.updateTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }

    fun getAllTransactionsForUser(userId: Int): Flow<List<Transaction>> {
        return transactionDao.getAllTransactionsForUser(userId)
    }

    fun getTransactionsForAccount(accountId: Int): Flow<List<Transaction>> {
        return transactionDao.getTransactionsForAccount(accountId)
    }

    fun getTransactionsForCategory(categoryId: Int): Flow<List<Transaction>> {
        return transactionDao.getTransactionsForCategory(categoryId)
    }

    suspend fun getTransactionById(transactionId: Int): Transaction? {
        return transactionDao.getTransactionById(transactionId)
    }

    suspend fun getTotalAmountByType(userId: Int, type: String): Int? {
        return transactionDao.getTotalAmountByType(userId, type)
    }

    fun getTransactionsWithDetails(userId: Int): Flow<List<TransactionWithDetails>> {
        return transactionDao.getTransactionsWithDetails(userId)
    }

}