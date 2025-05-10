package com.prince.bankr.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["user_id"])
    ]
)
data class Category(
    @PrimaryKey(autoGenerate = true) val category_id: Int = 0,
    val user_id: Int,
    val name: String,
    val monthly_limit: Int?
)
