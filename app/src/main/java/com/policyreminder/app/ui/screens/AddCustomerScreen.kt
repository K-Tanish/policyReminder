package com.policyreminder.app.ui.screens

import androidx.compose.foundation.background
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
import com.policyreminder.app.ui.viewmodel.CustomerViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCustomerScreen(
    viewModel: CustomerViewModel,
    onNavigateBack: () -> Unit,
    onCustomerAdded: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var firstName by remember { mutableStateOf("") }
    var middleName by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var mobileNumber by remember { mutableStateOf("") }
    var alternateMobile by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Customer",
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
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        width = 1.dp
                    )
                ) {
                    Text("Cancel")
                }
                
                Button(
                    onClick = {
                        // Validation
                        when {
                            firstName.isBlank() -> {
                                errorMessage = "First name is required"
                                showError = true
                            }
                            surname.isBlank() -> {
                                errorMessage = "Surname is required"
                                showError = true
                            }
                            mobileNumber.isBlank() -> {
                                errorMessage = "Mobile number is required"
                                showError = true
                            }
                            mobileNumber.length < 10 -> {
                                errorMessage = "Enter a valid mobile number"
                                showError = true
                            }
                            else -> {
                                isSaving = true
                                val customer = Customer(
                                    firstName = firstName.trim(),
                                    middleName = middleName.trim(),
                                    surname = surname.trim(),
                                    mobileNumber = mobileNumber.trim(),
                                    alternateMobile = alternateMobile.trim(),
                                    email = email.trim(),
                                    address = address.trim(),
                                    dateOfBirth = parseDateOfBirth(dateOfBirth)
                                )
                                
                                viewModel.addCustomer(customer) { customerId ->
                                    isSaving = false
                                    onCustomerAdded(customerId)
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
                        Text("Save Customer")
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
                text = "Personal Information",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            // First Name
            CustomTextField(
                value = firstName,
                onValueChange = { 
                    firstName = it
                    showError = false
                },
                label = "First Name *",
                leadingIcon = Icons.Outlined.Person
            )
            
            // Middle Name
            CustomTextField(
                value = middleName,
                onValueChange = { middleName = it },
                label = "Middle Name",
                leadingIcon = Icons.Outlined.Person
            )
            
            // Surname
            CustomTextField(
                value = surname,
                onValueChange = { 
                    surname = it
                    showError = false
                },
                label = "Surname *",
                leadingIcon = Icons.Outlined.Person
            )
            
            // Date of Birth
            CustomTextField(
                value = dateOfBirth,
                onValueChange = { dateOfBirth = it },
                label = "Date of Birth (DD/MM/YYYY)",
                leadingIcon = Icons.Outlined.Cake,
                keyboardType = KeyboardType.Number
            )
            
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            )
            
            Text(
                text = "Contact Information",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            
            // Mobile Number
            CustomTextField(
                value = mobileNumber,
                onValueChange = { 
                    if (it.length <= 10) {
                        mobileNumber = it
                        showError = false
                    }
                },
                label = "Mobile Number *",
                leadingIcon = Icons.Outlined.Phone,
                keyboardType = KeyboardType.Phone
            )
            
            // Alternate Mobile
            CustomTextField(
                value = alternateMobile,
                onValueChange = { 
                    if (it.length <= 10) {
                        alternateMobile = it
                    }
                },
                label = "Alternate Mobile",
                leadingIcon = Icons.Outlined.Phone,
                keyboardType = KeyboardType.Phone
            )
            
            // Email
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                leadingIcon = Icons.Outlined.Email,
                keyboardType = KeyboardType.Email
            )
            
            // Address
            CustomTextField(
                value = address,
                onValueChange = { address = it },
                label = "Address",
                leadingIcon = Icons.Outlined.LocationOn,
                singleLine = false,
                maxLines = 3
            )
            
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
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
        modifier = modifier.fillMaxWidth(),
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            unfocusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

private fun parseDateOfBirth(dateString: String): Long? {
    if (dateString.isBlank()) return null
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.parse(dateString)?.time
    } catch (e: Exception) {
        null
    }
}
