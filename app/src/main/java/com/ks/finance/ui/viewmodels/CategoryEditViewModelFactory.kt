package com.ks.finance.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ks.finance.data.BudgetDao

class CategoryEditViewModelFactory(
    private val dataSource: BudgetDao,
    private val categoryId: String?
        ) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryEditViewModel::class.java)) {
            return CategoryEditViewModel(dataSource, categoryId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}