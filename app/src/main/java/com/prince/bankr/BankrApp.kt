package com.prince.bankr

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.prince.bankr.navigation.AppNavigation
import com.prince.bankr.ui.theme.BankrTheme

@Composable
fun BankrApp() {
    BankrTheme {
        val navController = rememberNavController()
        Surface(color = MaterialTheme.colorScheme.background) {
            AppNavigation()
        }
    }
}