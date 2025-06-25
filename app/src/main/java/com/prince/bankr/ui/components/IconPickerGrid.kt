package com.prince.bankr.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.prince.bankr.utils.iconKeyToDrawableRes
import com.prince.bankr.utils.global.availableCategoryIcons

@Composable
fun IconPickerGrid(
    selectedIconKey: String,
    onIconSelected: (String) -> Unit
) {
    val columns = 4
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        items(availableCategoryIcons) { iconKey ->
            val isSelected = iconKey == selectedIconKey
            val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .size(56.dp)
                    .border(2.dp, borderColor, shape = CircleShape)
                    .clickable { onIconSelected(iconKey) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconKeyToDrawableRes(iconKey)),
                    contentDescription = iconKey,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
