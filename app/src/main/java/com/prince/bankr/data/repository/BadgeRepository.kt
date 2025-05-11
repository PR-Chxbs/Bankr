package com.prince.bankr.data.repository

import kotlinx.coroutines.flow.Flow
import com.prince.bankr.data.local.dao.BadgeDao
import com.prince.bankr.data.local.entities.Badge

class BadgeRepository (private val badgeDao: BadgeDao) {

    suspend fun insertBadge(badge: Badge) {
        badgeDao.insertBadge(badge)
    }

    suspend fun updateBadge(badge: Badge) {
        badgeDao.updateBadge(badge)
    }

    suspend fun deleteBadge(badge: Badge) {
        badgeDao.deleteBadge(badge)
    }

    fun getAllBadgesForUser(userId: Int): Flow<List<Badge>> {
        return badgeDao.getAllBadgesForUser(userId)
    }

    suspend fun getBadgeById(userId: Int, badgeId: Int): Badge {
        return badgeDao.getBadgeById(userId, badgeId)
    }
}