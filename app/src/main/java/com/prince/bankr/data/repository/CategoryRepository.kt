package com.prince.bankr.data.repository

import kotlinx.coroutines.flow.Flow
import com.prince.bankr.data.local.entities.Category
import com.prince.bankr.data.local.dao.CategoryDao

class CategoryRepository (private val categoryDao: CategoryDao) {

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    fun getAllCategories(userId: Int): Flow<List<Category>> {
        return categoryDao.getAllCategories(userId)
    }

    fun getCategoriesByMonthlyLimit(userId: Int, monthlyLimit: Int): Flow<List<Category>> {
        return categoryDao.getCategoriesByMonthlyLimit(userId, monthlyLimit)
    }

    suspend fun getCategoryById(categoryId: Int): Category? {
        return categoryDao.getCategoryById(categoryId)
    }
}