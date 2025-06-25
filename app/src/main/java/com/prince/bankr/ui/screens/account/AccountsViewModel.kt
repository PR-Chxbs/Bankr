package com.prince.bankr.ui.screens.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.SavedStateHandle

import com.prince.bankr.data.repository.AccountRepository

@HiltViewModel
class AccountsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AccountRepository
) : ViewModel() {

    // private val userId: Int = checkNotNull(savedStateHandle["userId"])
    private val userId: Int = -1

    private val _uiState = MutableStateFlow<AccountsUiState>(AccountsUiState.Loading)
    val uiState: StateFlow<AccountsUiState> = _uiState

    init {
        viewModelScope.launch {
            combine(
                repository.getAccountsForUser(userId),
                flow { emit(repository.getTotalUserBalance(userId)) }
            ) { accounts, totalBalance ->
                if (accounts.isEmpty()) {
                    AccountsUiState.Empty
                } else {
                    AccountsUiState.Success(accounts, totalBalance)
                }
            }.catch {
                _uiState.value = AccountsUiState.Error("Failed to load accounts")
            }.collect {
                _uiState.value = it
            }
        }
    }
}
