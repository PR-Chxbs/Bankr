package com.prince.bankr.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.prince.bankr.data.local.enums.Type
import java.util.Date

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["user_id"]),
        ForeignKey(entity = Account::class, parentColumns = ["account_id"], childColumns = ["account_id"])
    ]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true) val transaction_id: Int = 0,
    val user_id: Int,
    val account_id: Int,
    val category_id: Int,
    val amount: Int,
    val date: Date,
    val description: String,
    val type: Type,
    val receipt_image_path: String?
)