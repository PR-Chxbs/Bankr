package com.prince.bankr.ui.screens.budget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prince.bankr.data.local.entities.Transaction
import com.prince.bankr.data.local.rich.transaction.TransactionWithDetails
import com.prince.bankr.utils.iconKeyToDrawableRes
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BudgetScreen(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    onAddBudgetClick: () -> Unit,
    viewModel: BudgetViewModel = hiltViewModel()
) {
    val budgetGoal by viewModel.budgetGoal.collectAsState()
    val expenses by viewModel.expenses.collectAsState()
    val totalSpent by viewModel.totalSpent.collectAsState()
    val progress by viewModel.budgetProgress.collectAsState()
    val dailyAllowance by viewModel.dailyAllowance.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.badgeEvents.collect { msg ->
            snackbarHostState.showSnackbar(message = msg)
        }
    }

    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddBudgetClick) {
                Icon(Icons.Default.Add, contentDescription = "Set Budget")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = budgetGoal?.name ?: "No Budget Set",
                style = MaterialTheme.typography.headlineSmall
            )

            if (budgetGoal != null) {
                LinearProgressIndicator(
                    progress = progress.coerceIn(0f, 1f),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "R${totalSpent} of R${budgetGoal!!.total_budget} used",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "R$dailyAllowance per day until the end of the month",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Expenses This Month",
                style = MaterialTheme.typography.titleMedium
            )

            if (expenses.isEmpty()) {
                Text("No expenses recorded.")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(expenses) { txn ->
                        TransactionItem(txn = txn)
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionItem(txn: TransactionWithDetails) {
    val iconRes = iconKeyToDrawableRes(txn.category.iconKey)
    val formatter = remember { SimpleDateFormat("dd MMM yyyy", Locale.getDefault()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(MaterialTheme.colorScheme.background) // Theme background
            .border(1.dp, Color(0xFF767676), RoundedCornerShape(16.dp)), // Border
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "Category icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(txn.transaction.description, style = MaterialTheme.typography.bodyMedium)
                Text(
                    txn.account.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                "R${txn.transaction.amount}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

