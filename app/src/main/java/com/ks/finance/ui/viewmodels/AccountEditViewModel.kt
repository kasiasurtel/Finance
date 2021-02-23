package com.ks.finance.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ks.finance.data.Account
import com.ks.finance.data.AccountsDao
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

    fun onDelete() {               // TODO: add confirmation before delete
        viewModelScope.launch {
            _account.value?.let { database.delete(it) }
            _account.value = null
        }
    }

    fun onSave() {              // TODO
        viewModelScope.launch {
            _account.value?.let { database.update(it) } ?: Log.d("AccountEditViewModel", "no account!!!!!!!")
        }
    }
}