package com.prince.bankr.navigation

sealed class Screen(val route: String) {
    // --------- Auth routes ---------
    object Login: Screen("login")
    object Register: Screen("register")

    // --------- App routes ---------
    object Home: Screen("home")
    object Analytics: Screen("analytics")
    object AddTransaction: Screen("addTransaction")
    object Budget: Screen("budget")
    object Accounts: Screen("accounts")
}