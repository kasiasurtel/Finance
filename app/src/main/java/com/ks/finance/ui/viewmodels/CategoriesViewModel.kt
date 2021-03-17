package com.ks.finance.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ks.finance.data.BudgetDao
import com.ks.finance.data.Category
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val database: BudgetDao,
    application: Application
) : ViewModel() {

    val categories = database.getAllCategories()

    fun addCategory() {
        viewModelScope.launch {
            database.insert(Category(name="New category"))
        }
    }
}