package com.prince.bankr.ui.screens.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetBudgetScreen(
    bottomBar: @Composable () -> Unit,
    onBudgetSaved: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: SetBudgetViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var month by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.MONTH) + 1) } // Jan = 0
    var year by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    var totalBudget by remember { mutableIntStateOf(0) }

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
        },
        bottomBar = bottomBar
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Set Monthly Budget", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Budget Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = month.toString(),
                onValueChange = { month = it.toIntOrNull() ?: month },
                label = { Text("Month (1-12)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = year.toString(),
                onValueChange = { year = it.toIntOrNull() ?: year },
                label = { Text("Year (e.g. 2025)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = totalBudget.toString(),
                onValueChange = { totalBudget = it.toIntOrNull() ?: 0 },
                label = { Text("Total Budget (ZAR)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    viewModel.saveBudget(
                        name = name,
                        description = description,
                        month = month,
                        year = year,
                        totalBudget = totalBudget
                    )
                    onBudgetSaved()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Budget")
            }
        }
    }
}
