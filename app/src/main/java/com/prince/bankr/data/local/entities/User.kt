package com.prince.bankr.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val user_id: Int = 0,
    val username: String,
    val email: String,
    val password: String
)