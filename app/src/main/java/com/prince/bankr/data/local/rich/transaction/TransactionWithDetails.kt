package com.prince.bankr.data.local.rich.transaction

import androidx.room.Embedded
import androidx.room.Relation
import com.prince.bankr.data.local.entities.Account
import com.prince.bankr.data.local.entities.Category
import com.prince.bankr.data.local.entities.Transaction
import com.prince.bankr.navigation.Screen

data class TransactionWithDetails(
    @Embedded val transaction: Transaction,

    @Relation(
        parentColumn = "category_id",
        entityColumn = "category_id"
    )
    val category: Category,

    @Relation(
        parentColumn = "account_id",
        entityColumn = "account_id"
    )
    val account: Account
)
