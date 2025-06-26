package com.prince.bankr.ui.screens.account

import android.util.Log
import androidx.compose.runtime.collectAsState
import com.prince.bankr.data.local.entities.Account
import com.prince.bankr.data.repository.AccountRepository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val repository: AccountRepository
) : ViewModel() {

    fun addAccount(account: Account, onSuccess: () -> Unit) {
        Log.d("AccountsScreen", "(AddAccountViewModel) addAccount() triggered")
        Log.d("AccountsScreen", "(AddAccountViewModel) Account: $account")
        viewModelScope.launch {
            repository.insertAccount(account)
            val accounts = repository.getAccountsForUser(1)
            Log.d("AccountsScreen", "(AddAccountViewModel) All accounts:")
            accounts.collect { accountList ->
                accountList.forEach { account ->
                    Log.d("AccountsScreen", account.toString())
                }
            }
            onSuccess()
        }
    }
}
