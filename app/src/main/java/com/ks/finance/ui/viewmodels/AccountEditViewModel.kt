package com.ks.finance.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ks.finance.data.Account
import com.ks.finance.data.AccountsDao
import com.ks.finance.data.Currency
import kotlinx.coroutines.launch

class AccountEditViewModel(
    private val database: AccountsDao,
    accountId: String?
) : ViewModel() {

    private var _account = MutableLiveData<Account>().apply {
        viewModelScope.launch {
            accountId?.let { value = database.get(accountId.toLong()) }
        }
    }
    var account: LiveData<Account> = _account

    var _leave = MutableLiveData<Boolean>().apply { value = false }
    var leave: LiveData<Boolean> = _leave

    fun onDelete() {
        viewModelScope.launch {
            _account.value?.let { database.delete(it) }
            _account.value = null
            _leave.value = true
        }
    }

    fun onSave(name: String, currency: Currency, balance: Double) {
        viewModelScope.launch {
            if(_account.value != null) {
                _account.value!!.name = name
                _account.value!!.currency = currency
                _account.value!!.balance = balance
                database.update(_account.value!!)
            } else {
                val account = Account(name = name, currency = currency, balance = balance)
                _account.value = account
                database.insert(account)
            }
            _leave.value = true
        }
    }

    fun doneNavigating() { _leave.value = false }
}