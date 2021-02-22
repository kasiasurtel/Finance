package com.ks.finance.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo
    var balance: Double = 0.0,

    @ColumnInfo
    var currency: Currency = Currency.USD
    )