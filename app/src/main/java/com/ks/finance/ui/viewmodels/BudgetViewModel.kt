package com.ks.finance.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ks.finance.data.Account
import com.ks.finance.data.BudgetDao

class BudgetViewModel(
    database: BudgetDao
) : ViewModel() {

    val accounts = database.getAllAccounts()

    private var _accountIndex: Int = 0
    var accountIndex = _accountIndex

    private var _selectedAccount = MutableLiveData<Account>()
    var selectedAccount: LiveData<Account> = _selectedAccount

    fun getAccountsNames(): Array<String>? {
        Log.d("BudgetViewModel", "number of accounts: " + accounts.value?.size)
        return accounts.value?.map { it.name }?.toTypedArray()
    }

    fun selectAccount(position: Int) {
        accountIndex = position
    }

    fun setSelectedAccount() {
        _selectedAccount.value = accounts.value?.get(accountIndex)
    }

    fun getSelectedAccountPosition() = accounts.value!!.indexOf(selectedAccount.value)

}