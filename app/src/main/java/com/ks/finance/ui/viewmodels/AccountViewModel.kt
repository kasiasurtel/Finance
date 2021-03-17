package com.ks.finance.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ks.finance.data.Account
import com.ks.finance.data.BudgetDao
import kotlinx.coroutines.launch

class AccountViewModel(
    val database: BudgetDao,
    accountId: String?
) : ViewModel() {

    private var _account = MutableLiveData<Account>().apply {
        viewModelScope.launch {
            value = accountId?.let { database.getAccount(it.toLong()) }
        }
    }
    var account: LiveData<Account> = _account
}