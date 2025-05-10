package com.prince.bankr.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import java.util.Date

@Entity(
    tableName = "badges",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["user_id"])
    ]
)
data class Badge(
    @PrimaryKey( autoGenerate = true) val badge_id: Int = 0,
    val user_id: Int,
    val name: String,
    val description: String,
    val date_earned: Date
)
