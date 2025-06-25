package com.prince.bankr.utils

import androidx.compose.runtime.Composable
import com.prince.bankr.R

@Composable
fun iconKeyToDrawableRes(iconKey: String): Int {
    return when (iconKey) {
        "shopping_cart" -> R.drawable.ic_home_icon
        "food" -> R.drawable.ic_home_icon
        "transport" -> R.drawable.ic_home_icon
        "salary" -> R.drawable.ic_home_icon
        "entertainment" -> R.drawable.ic_home_icon
        "health" -> R.drawable.ic_home_icon
        "education" -> R.drawable.ic_home_icon
        "gift" -> R.drawable.ic_home_icon
        else -> R.drawable.ic_home_icon // fallback icon
    }
}
