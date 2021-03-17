package com.ks.finance.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ks.finance.data.Account
import com.ks.finance.data.BudgetDao
import com.ks.finance.data.Currency
import com.ks.finance.data.Transaction
import kotlinx.coroutines.launch

class BudgetViewModel(
    val database: BudgetDao
) : ViewModel() {

    val accounts = database.getAllAccounts()

    private var _accountIndex: Int = 0
    var accountIndex = _accountIndex

    private var _selectedAccount = MutableLiveData<Account>().apply {
        accounts.value?.get(0)
    }
    var selectedAccount: LiveData<Account> = _selectedAccount

    var transactions = database.getAllTransactions()
    var selectedTransactions = MutableLiveData<List<Transaction>>()

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

    fun addTransaction() {
        viewModelScope.launch {
            database.insert(
                Transaction(
                    date = "DATEEE", accountId = selectedAccount.value!!.id, categoryId = 0, description = "",
                    isIncome = true, amount = 0.0, currency = Currency.PLN
                )
            )
        }
    }

    fun sortTransactions() = transactions.value?.filter { it.accountId == _selectedAccount.value?.id }


}