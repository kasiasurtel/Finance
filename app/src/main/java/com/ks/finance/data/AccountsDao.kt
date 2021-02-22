package com.ks.finance.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AccountsDao {
    @Insert
    suspend fun insert(account: Account)

    @Update
    suspend fun update(account: Account)

    @Delete
    suspend fun delete(account: Account)

    @Query("SELECT * FROM accounts WHERE id = :key")
    suspend fun get(key: Long): Account?

    @Query("SELECT * FROM accounts ORDER BY id ASC")
    fun getAllAccounts(): LiveData<List<Account>>
}