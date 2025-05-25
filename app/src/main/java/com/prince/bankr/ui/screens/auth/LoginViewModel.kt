package com.prince.bankr.ui.screens.auth

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.bankr.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var errorMessage by mutableStateOf<String?>(null)

    fun loginUser(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val user = userRepository.login(email, password)
            if (user != null) {
                onSuccess()
            } else {
                errorMessage = "Invalid credentials. Please try again."
            }
        }
    }
}