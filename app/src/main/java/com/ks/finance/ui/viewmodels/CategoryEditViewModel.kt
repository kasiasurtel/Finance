package com.ks.finance.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ks.finance.data.*
import kotlinx.coroutines.launch

class CategoryEditViewModel(
    private val database: BudgetDao,
    categoryId: String?
) : ViewModel() {

    private var _category = MutableLiveData<Category>().apply {
        viewModelScope.launch {
            categoryId?.let { value = database.getCategory(categoryId.toLong()) }
        }
    }
    var category: LiveData<Category> = _category

    var _leave = MutableLiveData<Boolean>().apply { value = false }
    var leave: LiveData<Boolean> = _leave

    fun onDelete() {
        viewModelScope.launch {
            _category.value?.let { database.delete(it) }
            _category.value = null
            _leave.value = true
        }
    }

    fun onSave(name: String) {
        viewModelScope.launch {
            if(_category.value != null) {
                _category.value!!.name = name
                database.update(_category.value!!)
            } else {
                val category = Category(name = name)
                _category.value = category
                database.insert(category)
            }
            _leave.value = true
        }
    }

    fun doneNavigating() { _leave.value = false }
}