package com.prince.bankr.ui.screens.account

import com.prince.bankr.data.local.entities.Account

sealed class AccountsUiState {
    object Loading : AccountsUiState()
    data class Success(
        val accounts: List<Account>,
        val totalBalance: Int
    ) : AccountsUiState()
    object Empty : AccountsUiState()
    data class Error(val message: String) : AccountsUiState()
}