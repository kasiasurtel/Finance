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

    private var selectedAccountPosition: Int = 0

    private var _selectedAccount = MutableLiveData<Account>().apply {
        accounts.value?.get(0)
    }
    var selectedAccount: LiveData<Account> = _selectedAccount

    var transactions = database.getAllTransactions()

    fun getAccountsNames(): Array<String>? {
        Log.d("BudgetViewModel", "number of accounts: " + accounts.value?.size)
        return accounts.value?.map { it.name }?.toTypedArray()
    }

    fun selectAccount(position: Int) {
        selectedAccountPosition = position
        _selectedAccount.value = accounts.value?.get(position)

    }

    fun getSelectedAccountPosition() = selectedAccountPosition

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

    fun navigateLeft() {
        if(selectedAccountPosition > 0) {
            selectedAccountPosition--
            selectAccount(selectedAccountPosition)
        }
    }

    fun navigateRight() {
        if(selectedAccountPosition + 1 < accounts.value?.size!!) {
            selectedAccountPosition++
            selectAccount(selectedAccountPosition)
        }
    }


}