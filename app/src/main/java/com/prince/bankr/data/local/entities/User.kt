package com.prince.bankr.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["username"], unique = true),
        Index(value = ["email"], unique = true)
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true) val user_id: Int = 0,
    val username: String,
    val email: String,
    val password: String
)