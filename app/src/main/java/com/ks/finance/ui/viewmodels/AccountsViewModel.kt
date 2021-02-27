package com.ks.finance.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ks.finance.data.Account
import com.ks.finance.data.AccountsDao
import com.ks.finance.data.Currency
import kotlinx.coroutines.launch

class AccountsViewModel(
    val database: AccountsDao,
    val application: Application
) : ViewModel() {

    val accounts = database.getAllAccounts()

    fun onAccountClicked(id: Long) {
        //TODO: code is temporary
        viewModelScope.launch {
            val account = database.get(id)
            account?.let {
                account.name = "Klikniete"
                database.update(account)
            }
        }
    }

    private fun changeAccountExpansion(id: Long) {
        viewModelScope.launch {
            val account = database.get(id)
            account?.let {
               // account.isExpanded = account.isExpanded != true
                //database.update(it)
            }
        }
    }

    private fun deleteAccount(id: Long) {
        viewModelScope.launch {
            database.get(id)?.let { database.delete(it) }
        }
    }
}