package com.ks.finance.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo
    var date: String,

    @ColumnInfo
    var accountId: Long,

    @ColumnInfo
    var categoryId: Long,

    @ColumnInfo
    var description: String,

    @ColumnInfo
    var isIncome: Boolean,

    @ColumnInfo
    var amount: Double,

    @ColumnInfo
    var currency: Currency
)
