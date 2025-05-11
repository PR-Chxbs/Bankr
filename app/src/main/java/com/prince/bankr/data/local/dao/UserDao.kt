package com.prince.bankr.data.local.dao

import androidx.room.*
import com.prince.bankr.data.local.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE user_id = :userId")
    suspend fun getUserById(userId: Int): User?
}
