package com.prince.bankr.ui.screens.profileMenu

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prince.bankr.R
import com.prince.bankr.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileMenuScreen(
    navController: NavController,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile Menu") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {

            NavItem(
                itemName = "Category",
                iconRes = R.drawable.ic_home_icon,
                onHandleClick = {navController.navigate(Screen.Category.route)}
            )

            NavItem(
                itemName = "Badges",
                iconRes = R.drawable.ic_home_icon,
                onHandleClick = {navController.navigate(Screen.Badges.route)}
            )

            NavItem(
                itemName = "Settings",
                iconRes = R.drawable.ic_home_icon,
                onHandleClick = {navController.navigate(Screen.Category.route)}
            )

            NavItem(
                itemName = "Logout",
                iconRes = R.drawable.ic_home_icon,
                onHandleClick = {navController.navigate(Screen.Category.route)}
            )
        }
    }

}

@Composable
fun NavItem(
    itemName: String,
    iconRes: Int,
    onHandleClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = itemName,
            modifier = Modifier
                .size(20.dp)
        )
        Text(
            text = itemName,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onHandleClick()
                }
                .padding(12.dp)
        )
    }
}