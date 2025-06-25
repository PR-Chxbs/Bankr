package com.prince.bankr.ui.screens.profileMenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProfileMenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Settings",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    // Handle settings navigation
                }
                .padding(12.dp)
        )

        Text(
            text = "Logout",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    // Handle logout logic
                }
                .padding(12.dp)
        )
    }
}
