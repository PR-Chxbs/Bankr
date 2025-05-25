package com.prince.bankr.data.repository

import com.prince.bankr.data.local.dao.UserDao
import com.prince.bankr.data.local.entities.User

class UserRepository (private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }

    suspend fun login(email: String, password: String): User? {
        return userDao.getUserByCredentials(email, password)
    }
}