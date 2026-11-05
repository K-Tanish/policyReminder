package com.policyreminder.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.policyreminder.app.data.model.Customer
import com.policyreminder.app.data.model.Policy
import com.policyreminder.app.data.model.PolicyStatus
import com.policyreminder.app.ui.viewmodel.CustomerViewModel
import com.policyreminder.app.ui.viewmodel.PolicyViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPolicyScreen(
    policyViewModel: PolicyViewModel,
    customerViewModel: CustomerViewModel,
    onNavigateBack: () -> Unit,
    onPolicyAdded: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val customerUiState by customerViewModel.uiState.collectAsState()
    
    var selectedCustomer by remember { mutableStateOf<Customer?>(null) }
    var showCustomerPicker by remember { mutableStateOf(false) }
    
    var policyNumber by remember { mutableStateOf("") }
    var insuranceCompany by remember { mutableStateOf("") }
    var policyType by remember { mutableStateOf("") }
    var policyholderName by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var renewalDate by remember { mutableStateOf("") }
    var premiumAmount by remember { mutableStateOf("") }
    var paymentFrequency by remember { mutableStateOf("Yearly") }
    var notes by remember { mutableStateOf("") }
    
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }
    
    var showPaymentFrequencyMenu by remember { mutableStateOf(false) }
    var showPolicyTypeMenu by remember { mutableStateOf(false) }
    
    val policyTypes = listOf("Life Insurance", "Health Insurance", "Motor Insurance", "Home Insurance", "Travel Insurance", "Other")
    val paymentFrequencies = listOf("Yearly", "Half-yearly", "Quarterly", "Monthly")
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Policy",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Cancel")
                }
                
                Button(
                    onClick = {
                        when {
                            selectedCustomer == null -> {
                                errorMessage = "Please select a customer"
                                showError = true
                            }
                            policyNumber.isBlank() -> {
                                errorMessage = "Policy number is required"
                                showError = true
                            }
                            insuranceCompany.isBlank() -> {
                                errorMessage = "Insurance company is required"
                                showError = true
                            }
                            policyType.isBlank() -> {
                                errorMessage = "Policy type is required"
                                showError = true
                            }
                            policyholderName.isBlank() -> {
                                errorMessage = "Policyholder name is required"
                                showError = true
                            }
                            startDate.isBlank() -> {
                                errorMessage = "Start date is required"
                                showError = true
                            }
                            renewalDate.isBlank() -> {
                                errorMessage = "Renewal date is required"
                                showError = true
                            }
                            premiumAmount.isBlank() -> {
                                errorMessage = "Premium amount is required"
                                showError = true
                            }
                            else -> {
                                isSaving = true
                                val policy = Policy(
                                    customerId = selectedCustomer!!.id,
                                    policyNumber = policyNumber.trim(),
                                    insuranceCompany = insuranceCompany.trim(),
                                    policyType = policyType.trim(),
                                    policyholderName = policyholderName.trim(),
                                    startDate = parseDate(startDate) ?: System.currentTimeMillis(),
                                    renewalDate = parseDate(renewalDate) ?: System.currentTimeMillis(),
                                    premiumAmount = premiumAmount.toDoubleOrNull() ?: 0.0,
                                    paymentFrequency = paymentFrequency,
                                    status = PolicyStatus.ACTIVE,
                                    notes = notes.trim()
                                )
                                
                                policyViewModel.addPolicy(policy) { policyId ->
                                    isSaving = false
                                    onPolicyAdded(policyId)
                                }
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !isSaving,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.background
                        )
                    } else {
                        Text("Save Policy")
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Error message
            if (showError) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            
            Text(
                text = "Customer Selection",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            // Customer Selector
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showCustomerPicker = true },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (selectedCustomer != null) {
                        Column {
                            Text(
                                text = selectedCustomer!!.getFullName(),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = selectedCustomer!!.mobileNumber,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                            )
                        }
                    } else {
                        Text(
                            text = "Select Customer *",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                    }
                    Icon(
                        imageVector = Icons.Outlined.ArrowDropDown,
                        contentDescription = "Select",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
            
            Text(
                text = "Policy Information",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            CustomTextField(
                value = policyNumber,
                onValueChange = { 
                    policyNumber = it
                    showError = false
                },
                label = "Policy Number *",
                leadingIcon = Icons.Outlined.ConfirmationNumber
            )
            
            CustomTextField(
                value = insuranceCompany,
                onValueChange = { 
                    insuranceCompany = it
                    showError = false
                },
                label = "Insurance Company *",
                leadingIcon = Icons.Outlined.Business
            )
            
            // Policy Type Dropdown
            Box {
                CustomTextField(
                    value = policyType,
                    onValueChange = { },
                    label = "Policy Type *",
                    leadingIcon = Icons.Outlined.Category,
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showPolicyTypeMenu = true }) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDropDown,
                                contentDescription = "Select Policy Type",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
                DropdownMenu(
                    expanded = showPolicyTypeMenu,
                    onDismissRequest = { showPolicyTypeMenu = false }
                ) {
                    policyTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                policyType = type
                                showPolicyTypeMenu = false
                                showError = false
                            }
                        )
                    }
                }
            }
            
            CustomTextField(
                value = policyholderName,
                onValueChange = { 
                    policyholderName = it
                    showError = false
                },
                label = "Policyholder Name *",
                leadingIcon = Icons.Outlined.Person
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CustomTextField(
                    value = startDate,
                    onValueChange = { 
                        startDate = it
                        showError = false
                    },
                    label = "Start Date * (DD/MM/YYYY)",
                    leadingIcon = Icons.Outlined.CalendarToday,
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(1f)
                )
                
                CustomTextField(
                    value = renewalDate,
                    onValueChange = { 
                        renewalDate = it
                        showError = false
                    },
                    label = "Renewal Date * (DD/MM/YYYY)",
                    leadingIcon = Icons.Outlined.Event,
                    keyboardType = KeyboardType.Number,
                    modifier = Modifier.weight(1f)
                )
            }
            
            CustomTextField(
                value = premiumAmount,
                onValueChange = { 
                    premiumAmount = it
                    showError = false
                },
                label = "Premium Amount *",
                leadingIcon = Icons.Outlined.CurrencyRupee,
                keyboardType = KeyboardType.Decimal
            )
            
            // Payment Frequency Dropdown
            Box {
                CustomTextField(
                    value = paymentFrequency,
                    onValueChange = { },
                    label = "Payment Frequency",
                    leadingIcon = Icons.Outlined.Schedule,
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showPaymentFrequencyMenu = true }) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDropDown,
                                contentDescription = "Select Frequency",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
                DropdownMenu(
                    expanded = showPaymentFrequencyMenu,
                    onDismissRequest = { showPaymentFrequencyMenu = false }
                ) {
                    paymentFrequencies.forEach { frequency ->
                        DropdownMenuItem(
                            text = { Text(frequency) },
                            onClick = {
                                paymentFrequency = frequency
                                showPaymentFrequencyMenu = false
                            }
                        )
                    }
                }
            }
            
            CustomTextField(
                value = notes,
                onValueChange = { notes = it },
                label = "Notes (Optional)",
                leadingIcon = Icons.Outlined.Notes,
                singleLine = false,
                maxLines = 3
            )
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
    
    // Customer Picker Dialog
    if (showCustomerPicker) {
        CustomerPickerDialog(
            customers = customerUiState.customers,
            onCustomerSelected = { customer ->
                selectedCustomer = customer
                policyholderName = customer.getFullName()
                showCustomerPicker = false
                showError = false
            },
            onDismiss = { showCustomerPicker = false }
        )
    }
}

@Composable
private fun CustomerPickerDialog(
    customers: List<Customer>,
    onCustomerSelected: (Customer) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Customer") },
        text = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(customers) { customer ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCustomerSelected(customer) },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = customer.getFullName(),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = customer.mobileNumber,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        textContentColor = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
        },
        trailingIcon = trailingIcon,
        modifier = modifier.fillMaxWidth(),
        singleLine = singleLine,
        maxLines = maxLines,
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            unfocusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            cursorColor = MaterialTheme.colorScheme.primary,
            disabledTextColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

private fun parseDate(dateString: String): Long? {
    if (dateString.isBlank()) return null
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.parse(dateString)?.time
    } catch (e: Exception) {
        null
    }
}
