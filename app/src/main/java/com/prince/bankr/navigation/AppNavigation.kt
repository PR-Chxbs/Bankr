package com.prince.bankr.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prince.bankr.ui.screens.addTransaction.AddTransactionScreen
import com.prince.bankr.ui.screens.auth.LoginScreen
import com.prince.bankr.ui.screens.auth.RegisterScreen
//import com.prince.bankr.ui.screens.home.HomeScreen
import com.prince.bankr.ui.components.BottomNavItem
import com.prince.bankr.R
import com.prince.bankr.ui.components.BottomNavBar
import com.prince.bankr.ui.components.HomeTopBar
import com.prince.bankr.ui.screens.PlaceholderScreen
import com.prince.bankr.ui.screens.account.AccountsScreen
import com.prince.bankr.ui.screens.category.AddCategoryScreen
import com.prince.bankr.ui.screens.category.CategoriesScreen
import com.prince.bankr.ui.screens.profileMenu.ProfileMenuScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val bottomNavItems = listOf(
        BottomNavItem(
            route = Screen.Home.route,
            label = "Home",
            // iconRes = R.drawable.ic_dashboard
            iconRes = R.drawable.ic_home_icon
        ),
        BottomNavItem(
            route = Screen.Analytics.route,
            label = "Analytics",
            iconRes = R.drawable.ic_home_icon
        ),
        BottomNavItem(
            route = Screen.AddTransaction.route,
            label = "Add",
            iconRes = R.drawable.ic_home_icon
        ),
        BottomNavItem(
            route = Screen.Budget.route,
            label = "Budget",
            iconRes = R.drawable.ic_home_icon
        ),
        BottomNavItem(
            route = Screen.Accounts.route,
            label = "Accounts",
            iconRes = R.drawable.ic_home_icon
        )

    )

    NavHost(navController, startDestination = Screen.Login.route) {

        // --------- Auth Routes ---------

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Home.route)
                    { popUpTo(Screen.Login.route)
                        { inclusive = true }
                    }
                },
                onGoToRegister = { navController.navigate(Screen.Register.route)}
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Screen.Home.route)
                    { popUpTo(Screen.Register.route)
                        { inclusive = true }
                    }
                },
                onGoToLogin = { navController.navigate(Screen.Login.route)}
            )
        }


        // --------- Main App Routes ---------

        composable(Screen.Home.route) {
            PlaceholderScreen(
                topBar = {
                    HomeTopBar(
                        navController = navController
                    )
                },
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.Home.route
                    )
                },
                screen = "Home"
            )
        }

        composable(Screen.Analytics.route) {
            PlaceholderScreen(
                topBar = {
                    HomeTopBar(
                        navController = navController
                    )
                },
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.Analytics.route
                    )
                },
                screen = "Analytics"
            )
        }

        composable(Screen.AddTransaction.route) {
            AddTransactionScreen(
                topBar = {
                    HomeTopBar(
                        navController = navController
                    )
                },
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.AddTransaction.route
                    )
                }
            )
        }

        composable(Screen.Budget.route) {
            PlaceholderScreen(
                topBar = {
                    HomeTopBar(
                        navController = navController
                    )
                },
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.Budget.route
                    )
                },
                screen = "Budget"
            )
        }

        composable(Screen.Accounts.route) {
            AccountsScreen(
                topBar = {
                    HomeTopBar(
                        navController = navController
                    )
                },
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.Accounts.route
                    )
                },
                onAddAccountClick = {}
            )
        }

        // Account extras
        composable(Screen.AddAccount.route) {
            AddTransactionScreen(
                topBar = {
                    HomeTopBar(
                        navController = navController
                    )
                },
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.Accounts.route
                    )
                }

            )
        }

        // --------- Profile Menu Routes ---------
        composable(Screen.ProfileMenu.route) {
            ProfileMenuScreen(
                navController = navController,
                onBackClick = {navController.navigate(Screen.Home.route)}
            )
        }

        composable(Screen.Category.route) {
            CategoriesScreen(
                onBackClick = {navController.navigate(Screen.ProfileMenu.route)},
                onAddCategoryClick = {navController.navigate(Screen.AddCategory.route)}
            )
        }

        composable(Screen.AddCategory.route) {
            AddCategoryScreen(
                onBackClick = {navController.navigate(Screen.Category.route)},
                onCategorySaved = {navController.navigate(Screen.Category.route)}
            )
        }
    }
}
