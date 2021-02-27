package com.ks.finance.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CategoriesDao {
    @Insert
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM categories WHERE id = :key")
    suspend fun get(key: Long): Category?

    @Query("SELECT * FROM categories ORDER BY id ASC")
    fun getAllCategories(): LiveData<List<Category>>
}