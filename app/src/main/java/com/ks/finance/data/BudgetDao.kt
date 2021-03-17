package com.ks.finance.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BudgetDao {
    @Insert
    suspend fun insert(account: Account)

    @Update
    suspend fun update(account: Account)

    @Delete
    suspend fun delete(account: Account)

    @Query("SELECT * FROM accounts WHERE id = :key")
    suspend fun getAccount(key: Long): Account?

    @Query("SELECT * FROM accounts ORDER BY id ASC")
    fun getAllAccounts(): LiveData<List<Account>>

    @Insert
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM categories WHERE id = :key")
    suspend fun getCategory(key: Long): Category?

    @Query("SELECT * FROM categories ORDER BY id ASC")
    fun getAllCategories(): LiveData<List<Category>>
}