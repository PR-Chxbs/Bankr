package com.prince.bankr.ui.components

import androidx.annotation.DrawableRes

data class BottomNavItem(
    val route: String,
    val label: String,
    @DrawableRes val iconRes: Int
)
