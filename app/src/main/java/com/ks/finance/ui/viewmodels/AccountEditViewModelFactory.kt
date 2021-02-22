package com.ks.finance.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ks.finance.data.AccountsDao

class AccountEditViewModelFactory(
    private val dataSource: AccountsDao,
    private val accountId: Long
        ) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountEditViewModel::class.java)) {
            return AccountEditViewModel(dataSource, accountId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}