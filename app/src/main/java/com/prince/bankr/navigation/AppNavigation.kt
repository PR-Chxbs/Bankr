package com.prince.bankr.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prince.bankr.ui.screens.auth.LoginScreen
import com.prince.bankr.ui.screens.auth.RegisterScreen
//import com.prince.bankr.ui.screens.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("home")
                    { popUpTo("login")
                        { inclusive = true }
                    }
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate("home")
                    { popUpTo("register")
                        { inclusive = true }
                    }
                }
            )
        }

        /*composable("home") {
            HomeScreen()
        }*/
    }
}
