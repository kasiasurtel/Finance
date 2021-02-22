package com.ks.finance.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ks.finance.data.AccountsDao

class AccountsViewModelFactory(
    private val dataSource: AccountsDao,
    private val application: Application
        ) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountsViewModel::class.java)) {
            return AccountsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}