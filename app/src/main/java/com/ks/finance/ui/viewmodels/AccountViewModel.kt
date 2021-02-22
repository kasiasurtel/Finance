package com.ks.finance.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ks.finance.data.Account
import com.ks.finance.data.AccountsDao
import kotlinx.coroutines.launch

class AccountViewModel(
    val database: AccountsDao,
    accountId: Long
) : ViewModel() {

    private var _account = MutableLiveData<Account>().apply {
        viewModelScope.launch {
            value = database.get(accountId)
        }
    }
    var account: LiveData<Account> = _account
}