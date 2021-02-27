package com.ks.finance.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val isIncome: Boolean = true
)
