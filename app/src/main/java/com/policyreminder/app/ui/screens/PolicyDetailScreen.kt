package com.policyreminder.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.policyreminder.app.data.model.Policy
import com.policyreminder.app.data.model.RenewalHistory
import com.policyreminder.app.ui.theme.Foreground15
import com.policyreminder.app.ui.viewmodel.PolicyViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolicyDetailScreen(
    policyId: Long,
    viewModel: PolicyViewModel,
    onNavigateBack: () -> Unit,
    onEditClick: () -> Unit,
    onCallCustomer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val detailState by viewModel.getPolicyDetail(policyId).collectAsState(
        initial = com.policyreminder.app.ui.viewmodel.PolicyDetailUiState()
    )
    
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showRenewDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Policy Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Outlined.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Outlined.Edit, "Edit")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Outlined.Delete, "Delete")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        if (detailState.isLoading) {
            Box(Modifier.fillMaxSize().padding(paddingValues), Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else {
            detailState.policy?.let { policy ->
                LazyColumn(
                    Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header Card
                    item {
                        PolicyHeaderCard(
                            policy = policy,
                            customerName = detailState.customerName,
                            onRenewClick = { showRenewDialog = true },
                            onCallClick = onCallCustomer
                        )
                    }
                    
                    // Policy Information
                    item {
                        InfoSection(
                            "Policy Information",
                            listOf(
                                "Policy Number" to policy.policyNumber,
                                "Insurance Company" to policy.insuranceCompany,
                                "Policy Type" to policy.policyType,
                                "Policyholder" to policy.policyholderName,
                                "Status" to policy.getCurrentStatus().name
                            )
                        )
                    }
                    
                    // Financial Information
                    item {
                        InfoSection(
                            "Financial Information",
                            listOf(
                                "Premium Amount" to formatCurrency(policy.premiumAmount),
                                "Payment Frequency" to policy.paymentFrequency,
                                "Start Date" to formatDate(policy.startDate),
                                "Renewal Date" to formatDate(policy.renewalDate),
                                "Days Until Renewal" to "${policy.getDaysUntilRenewal()} days"
                            )
                        )
                    }
                    
                    if (policy.notes.isNotEmpty()) {
                        item {
                            Column {
                                Text("Notes", style = MaterialTheme.typography.titleMedium, 
                                     color = MaterialTheme.colorScheme.primary)
                                Spacer(Modifier.height(8.dp))
                                Card(Modifier.fillMaxWidth(), 
                                     colors = CardDefaults.cardColors(containerColor = Foreground15)) {
                                    Text(policy.notes, Modifier.padding(16.dp),
                                         style = MaterialTheme.typography.bodyMedium,
                                         color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                    
                    // Renewal History
                    item {
                        Text("Renewal History", style = MaterialTheme.typography.titleMedium,
                             color = MaterialTheme.colorScheme.primary)
                    }
                    
                    if (detailState.renewalHistory.isEmpty()) {
                        item {
                            Card(Modifier.fillMaxWidth().padding(vertical = 12.dp),
                                 colors = CardDefaults.cardColors(containerColor = Foreground15)) {
                                Box(Modifier.padding(24.dp), Alignment.Center) {
                                    Text("No renewal history yet",
                                         style = MaterialTheme.typography.bodyMedium,
                                         color = MaterialTheme.colorScheme.primary.copy(0.5f))
                                }
                            }
                        }
                    } else {
                        items(detailState.renewalHistory) { history ->
                            RenewalHistoryItem(history)
                        }
                    }
                }
            }
        }
    }
    
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Policy?") },
            text = { Text("This will permanently delete the policy and all associated data.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        detailState.policy?.let { viewModel.deletePolicy(it) { onNavigateBack() } }
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) { Text("Delete") }
            },
            dismissButton = { TextButton({ showDeleteDialog = false }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun PolicyHeaderCard(
    policy: Policy,
    customerName: String,
    onRenewClick: () -> Unit,
    onCallClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Foreground15)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Description,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = policy.policyType,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = customerName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary.copy(0.6f)
                )
                Text(
                    text = policy.insuranceCompany,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary.copy(0.5f)
                )
            }
            
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onRenewClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = "Renew",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Renew")
                }
                OutlinedButton(
                    onClick = onCallClick,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = "Call",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Call")
                }
            }
        }
    }
}

@Composable
private fun InfoSection(
    title: String,
    items: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Foreground15)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items.forEach { (label, value) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                        Text(
                            text = value,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RenewalHistoryItem(history: RenewalHistory) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Foreground15)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Renewed on ${formatDate(history.renewedAt)}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Old Date:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary.copy(0.6f)
                )
                Text(
                    text = formatDate(history.previousRenewalDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "New Date:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary.copy(0.6f)
                )
                Text(
                    text = formatDate(history.newRenewalDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Premium:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary.copy(0.6f)
                )
                Text(
                    text = "${formatCurrency(history.previousPremium)} → ${formatCurrency(history.newPremium)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            if (history.remarks.isNotEmpty()) {
                Text(
                    text = history.remarks,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary.copy(0.7f)
                )
            }
        }
    }
}

private fun formatCurrency(amount: Double) = 
    NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(amount)

private fun formatDate(timestamp: Long) = 
    SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(timestamp))
