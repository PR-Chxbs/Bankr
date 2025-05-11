package com.prince.bankr.data.local.dao

import androidx.room.*
import com.prince.bankr.data.local.entities.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM categories WHERE user_id = :userId")
    fun getAllCategories(userId: Int): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE user_id = :userId AND monthly_limit <= :monthlyLimit")
    fun getCategoriesByMonthlyLimit(userId: Int, monthlyLimit: Int): Flow<List<Category>>

    @Query("SELECT * FROM categories WHERE category_id = :categoryId")
    suspend fun getCategoryById(categoryId: Int): Category?
}