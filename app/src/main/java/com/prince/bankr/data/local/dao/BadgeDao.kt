package com.prince.bankr.data.local.dao

import androidx.room.*
import com.prince.bankr.data.local.entities.Badge
import kotlinx.coroutines.flow.Flow

@Dao
interface BadgeDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBadge(badge: Badge)

    @Update
    suspend fun updateBadge(badge: Badge)

    @Delete
    suspend fun deleteBadge(badge: Badge)

    @Query("SELECT * FROM badges WHERE user_id = :userId")
    fun getAllBadgesForUser(userId: Int): Flow<List<Badge>>

    @Query("SELECT * FROM badges WHERE user_id = :userId AND badge_id = :badgeId")
    suspend fun getBadgeById(userId: Int, badgeId: Int): Badge
}