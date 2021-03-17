package com.ks.finance.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ks.finance.data.BudgetDao
import kotlinx.coroutines.launch

class AccountsViewModel(
    val database: BudgetDao,
    val application: Application
) : ViewModel() {

    val accounts = database.getAllAccounts()

    fun onAccountClicked(id: Long) {
        //TODO: code is temporary
        viewModelScope.launch {
            val account = database.getAccount(id)
            account?.let {
                account.name = "Klikniete"
                database.update(account)
            }
        }
    }

    private fun changeAccountExpansion(id: Long) {
        viewModelScope.launch {
            val account = database.getAccount(id)
            account?.let {
               // account.isExpanded = account.isExpanded != true
                //database.update(it)
            }
        }
    }

    private fun deleteAccount(id: Long) {
        viewModelScope.launch {
            database.getAccount(id)?.let { database.delete(it) }
        }
    }
}