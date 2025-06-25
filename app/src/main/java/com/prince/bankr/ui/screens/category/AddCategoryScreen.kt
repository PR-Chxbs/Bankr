package com.prince.bankr.ui.screens.category

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewModelScope
import com.prince.bankr.ui.components.IconPickerGrid
import com.prince.bankr.utils.global.availableCategoryIcons
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryScreen(
    onBackClick: () -> Unit,
    onCategorySaved: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var limitText by remember { mutableStateOf("") }
    var selectedIconKey by remember { mutableStateOf(availableCategoryIcons.first()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Category") },
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Name input
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Category Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Monthly Limit input
            OutlinedTextField(
                value = limitText,
                onValueChange = { limitText = it.filter { c -> c.isDigit() } },
                label = { Text("Monthly Limit (Optional)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Text("Choose an Icon:", style = MaterialTheme.typography.titleMedium)

            IconPickerGrid(
                selectedIconKey = selectedIconKey,
                onIconSelected = { selectedIconKey = it }
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    val limit = limitText.toIntOrNull()
                    if (name.isNotBlank()) {
                        viewModel.viewModelScope.launch {
                            viewModel.addCategory(name.trim(), limit, selectedIconKey)
                            Toast.makeText(context, "Category Added", Toast.LENGTH_SHORT).show()
                            onCategorySaved()
                        }
                    } else {
                        Toast.makeText(context, "Name is required", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Save Category")
            }
        }
    }
}
