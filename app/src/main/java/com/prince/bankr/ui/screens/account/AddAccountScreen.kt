package com.prince.bankr.ui.screens.account

import androidx.compose.foundation.border
import com.prince.bankr.data.local.entities.Account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountScreen(
    userId: Int = 1,
    onBackClick: () -> Unit,
    viewModel: AddAccountViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<String?>(null) }

    val accountTypes = listOf("Cash", "Credit", "Savings", "Investment")
    val imageUris = listOf(
        "drawable/ac_fnb.png",
        "drawable/ac_fnb.png",
        "drawable/ac_fnb.png",
        "drawable/ac_fnb.png"
    ) // Make sure these are accessible (via resources or assets)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Account") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Account Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = balance,
                onValueChange = { if (it.all { c -> c.isDigit() }) balance = it },
                label = { Text("Initial Balance (R)") },
                modifier = Modifier.fillMaxWidth()
            )

            // Type dropdown
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = type,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Account Type") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    accountTypes.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                type = it
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Image selector
            Text("Choose Account Image", style = MaterialTheme.typography.titleSmall)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(imageUris) { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .clickable { selectedImageUri = uri }
                            .border(
                                width = if (selectedImageUri == uri) 2.dp else 0.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (name.isNotBlank() && type.isNotBlank() && selectedImageUri != null && balance.isNotBlank()) {
                        val account = Account(
                            user_id = userId,
                            name = name,
                            type = type,
                            image_uri = selectedImageUri!!,
                            balance = balance.toInt()
                        )
                        viewModel.addAccount(account) {
                            onBackClick()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && type.isNotBlank() && selectedImageUri != null && balance.isNotBlank()
            ) {
                Text("Save Account")
            }
        }
    }
}
