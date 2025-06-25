package com.prince.bankr.ui.screens.addTransaction

// Compose imports
import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.prince.bankr.data.local.enums.Type
import com.prince.bankr.ui.components.DropdownSelector
import com.prince.bankr.ui.components.SegmentedButton
import com.prince.bankr.utils.saveImageToInternalStorage
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    viewModel: AddTransactionViewModel = hiltViewModel(),
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
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

    // Image picker
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val path = saveImageToInternalStorage(context, it)
            viewModel.receiptImagePath = path
        }
    }

    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SegmentedButton(
                options = Type.entries.toList(),
                selectedIndex = selectedType,
                onOptionSelected = { viewModel.transactionType = it }
            )

            OutlinedTextField(
                value = viewModel.amount.toString(),
                onValueChange = { viewModel.amount = it.toIntOrNull() ?: 0 },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.description,
                onValueChange = { viewModel.description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )

            DropdownSelector(
                label = "Select Account",
                options = accounts,
                selectedOption = selectedAccount,
                onOptionSelected = { viewModel.selectedAccountId = it.account_id },
                optionLabel = { it.name }
            )

            DropdownSelector(
                label = "Select Category",
                options = categories,
                selectedOption = selectedCategory,
                onOptionSelected = { viewModel.selectedCategoryId = it.category_id },
                optionLabel = { it.name }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
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
                    .padding(vertical = 4.dp)
            ) {
                Icon(Icons.Default.CalendarToday, contentDescription = "Pick date")
                Spacer(Modifier.width(8.dp))
                Text("Date: $formattedDate")
            }

            // Attach Image Button
            Button(onClick = { launcher.launch("image/*") }) {
                Text("Attach Receipt Image")
            }

            // Show selected image preview
            viewModel.receiptImagePath?.let { path ->
                if (File(path).exists()) {
                    Image(
                        painter = rememberAsyncImagePainter(model = File(path)),
                        contentDescription = "Receipt Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text("Image not found at path: $path", color = Color.Red)
                }
            }

            Button(
                onClick = {
                    viewModel.addTransaction()
                    Toast.makeText(context, "Transaction Added", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Add Transaction")
            }
        }
    }
}
