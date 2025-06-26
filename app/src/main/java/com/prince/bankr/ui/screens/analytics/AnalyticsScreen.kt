package com.prince.bankr.ui.screens.analytics

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.prince.bankr.ui.components.PieChart
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AnalyticsScreen(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpenses by viewModel.totalExpenses.collectAsState()
    val expensesByCategory by viewModel.expensesByCategory.collectAsState()
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val formatter = remember { SimpleDateFormat("MMM yyyy", Locale.getDefault()) }
    val selectedDate = calendar.time
    val formattedDate = formatter.format(selectedDate)

    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val current = Calendar.getInstance()
                DatePickerDialog(
                    context,
                    { _, year, month, _ ->
                        viewModel.setDate(month + 1, year)
                    },
                    current.get(Calendar.YEAR),
                    current.get(Calendar.MONTH),
                    current.get(Calendar.DAY_OF_MONTH)
                ).show()
            }) {
                Icon(Icons.Default.CalendarToday, contentDescription = "Pick Month")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Analytics for $formattedDate",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text("Total Income: R$totalIncome")
            Text("Total Expenses: R$totalExpenses")

            Spacer(modifier = Modifier.height(16.dp))

            if (expensesByCategory.isNotEmpty()) {
                Text("Expenses by Category")
                PieChart(data = expensesByCategory.map { it.category.name to it.totalAmount })
            } else {
                Text("No expenses recorded for selected month.")
            }
        }
    }
}
