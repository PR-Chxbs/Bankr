package com.prince.bankr.ui.screens.addtransaction

// Core Compose
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

// Icons
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday

// ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.hilt.navigation.compose.hiltViewModel

// Date handling
import android.app.DatePickerDialog
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

// Data model
import com.prince.bankr.data.local.enums.Type

// Components
import com.prince.bankr.ui.components.DropdownSelector
import com.prince.bankr.ui.components.SegmentedButton

@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val categories = viewModel.categories.value
    val accounts by viewModel.accounts.collectAsState(initial = emptyList())

    val selectedAccount = accounts.find { it.account_id == viewModel.selectedAccountId }
    val selectedCategory = categories.find { it.category_id == viewModel.selectedCategoryId }
    val selectedType = viewModel.transactionType
    val formattedDate = remember(viewModel.date) {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(viewModel.date)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Amount
        OutlinedTextField(
            value = viewModel.amount.toString(),
            onValueChange = { viewModel.amount = it.toIntOrNull() ?: 0 },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Description
        OutlinedTextField(
            value = viewModel.description,
            onValueChange = { viewModel.description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        // Account dropdown
        DropdownSelector(
            label = "Select Account",
            options = accounts,
            selectedOption = selectedAccount,
            onOptionSelected = { viewModel.selectedAccountId = it.account_id },
            optionLabel = { it.name }
        )

        // Category dropdown
        DropdownSelector(
            label = "Select Category",
            options = categories,
            selectedOption = selectedCategory,
            onOptionSelected = { viewModel.selectedCategoryId = it.category_id },
            optionLabel = { it.name }
        )

        // Transaction type selector
        SegmentedButton(
            options = Type.entries.toList(),
            selectedIndex = selectedType,
            onOptionSelected = { viewModel.transactionType = it }
        )

        // Date picker
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                val calendar = Calendar.getInstance().apply { time = viewModel.date }
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val newDate = Calendar.getInstance().apply {
                            set(year, month, dayOfMonth)
                        }.time
                        viewModel.date = newDate
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        ) {
            Icon(Icons.Default.CalendarToday, contentDescription = "Pick date")
            Spacer(Modifier.width(8.dp))
            Text("Date: $formattedDate")
        }

        // Submit button
        Button(onClick = {
            viewModel.addTransaction()
            Toast.makeText(context, "Transaction Added", Toast.LENGTH_SHORT).show()
        }) {
            Text("Add Transaction")
        }
    }
}