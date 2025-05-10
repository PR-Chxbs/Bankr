package com.prince.bankr.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "accounts",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["user_id"])
    ]
)
data class Account(
    @PrimaryKey(autoGenerate = true) val account_id: Int = 0,
    val name: String,
    val image_uri: String,
    val balance: Int,
    val type: String
)
