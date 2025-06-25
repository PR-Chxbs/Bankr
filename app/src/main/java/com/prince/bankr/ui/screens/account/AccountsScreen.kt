package com.prince.bankr.ui.screens.account

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import androidx.hilt.navigation.compose.hiltViewModel
import com.prince.bankr.data.local.entities.Account


@Composable
fun AccountsScreen(
    userId: Int = 1,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    viewModel: AccountsViewModel = hiltViewModel(),
    onAddAccountClick: () -> Unit
) {
    Log.d("AccountsScreen", "(AccountsScreen) Loaded composable")
    val uiState by viewModel.uiState.collectAsState()

    Log.d("AccountsScreen", "(AccountsScreen) UI State: $uiState")
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(onClick = onAddAccountClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Account")
            }
        }
    ) { padding ->
        when (uiState) {
            is AccountsUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is AccountsUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text("Failed to load accounts")
                }
            }

            is AccountsUiState.Empty -> {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text("No accounts found.")
                }
            }

            is AccountsUiState.Success -> {
                val accounts = (uiState as AccountsUiState.Success).accounts
                val totalBalance = (uiState as AccountsUiState.Success).totalBalance

                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)) {

                    Text(
                        text = "Total Balance: R$totalBalance",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(accounts) { account ->
                            AccountCard(account = account)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AccountCard(account: Account) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = account.image_uri,
                contentDescription = account.name,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = account.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = account.type,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "R${account.balance}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
