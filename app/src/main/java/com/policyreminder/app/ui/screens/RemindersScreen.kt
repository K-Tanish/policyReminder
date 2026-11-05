package com.policyreminder.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.policyreminder.app.data.model.ReminderType
import com.policyreminder.app.ui.components.ReminderItem
import com.policyreminder.app.ui.viewmodel.ReminderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemindersScreen(
    viewModel: ReminderViewModel,
    onReminderClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedFilter by remember { mutableStateOf<ReminderType?>(null) }
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Reminders",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    if (uiState.reminders.isNotEmpty()) {
                        Badge(
                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            contentColor = MaterialTheme.colorScheme.primary
                        ) {
                            Text(
                                text = uiState.reminders.size.toString(),
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Type Filters
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        ReminderFilterChip(
                            label = "All",
                            icon = Icons.Outlined.FilterList,
                            count = uiState.reminders.size,
                            isSelected = selectedFilter == null,
                            onClick = {
                                selectedFilter = null
                                viewModel.filterByType(null)
                            }
                        )
                    }
                    item {
                        ReminderFilterChip(
                            label = "Renewals",
                            icon = Icons.Outlined.Refresh,
                            count = uiState.reminders.count { it.reminder.type == ReminderType.POLICY_RENEWAL },
                            isSelected = selectedFilter == ReminderType.POLICY_RENEWAL,
                            onClick = {
                                selectedFilter = if (selectedFilter == ReminderType.POLICY_RENEWAL) null else ReminderType.POLICY_RENEWAL
                                viewModel.filterByType(if (selectedFilter == ReminderType.POLICY_RENEWAL) null else ReminderType.POLICY_RENEWAL)
                            }
                        )
                    }
                    item {
                        ReminderFilterChip(
                            label = "Birthdays",
                            icon = Icons.Outlined.Cake,
                            count = uiState.reminders.count { it.reminder.type == ReminderType.BIRTHDAY },
                            isSelected = selectedFilter == ReminderType.BIRTHDAY,
                            onClick = {
                                selectedFilter = if (selectedFilter == ReminderType.BIRTHDAY) null else ReminderType.BIRTHDAY
                                viewModel.filterByType(if (selectedFilter == ReminderType.BIRTHDAY) null else ReminderType.BIRTHDAY)
                            }
                        )
                    }
                    item {
                        ReminderFilterChip(
                            label = "Anniversaries",
                            icon = Icons.Outlined.Favorite,
                            count = uiState.reminders.count { it.reminder.type == ReminderType.ANNIVERSARY },
                            isSelected = selectedFilter == ReminderType.ANNIVERSARY,
                            onClick = {
                                selectedFilter = if (selectedFilter == ReminderType.ANNIVERSARY) null else ReminderType.ANNIVERSARY
                                viewModel.filterByType(if (selectedFilter == ReminderType.ANNIVERSARY) null else ReminderType.ANNIVERSARY)
                            }
                        )
                    }
                    item {
                        ReminderFilterChip(
                            label = "Follow-ups",
                            icon = Icons.Outlined.Phone,
                            count = uiState.reminders.count { it.reminder.type == ReminderType.FOLLOW_UP_CALL },
                            isSelected = selectedFilter == ReminderType.FOLLOW_UP_CALL,
                            onClick = {
                                selectedFilter = if (selectedFilter == ReminderType.FOLLOW_UP_CALL) null else ReminderType.FOLLOW_UP_CALL
                                viewModel.filterByType(if (selectedFilter == ReminderType.FOLLOW_UP_CALL) null else ReminderType.FOLLOW_UP_CALL)
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (uiState.reminders.isEmpty()) {
            EmptyRemindersState(
                hasFilter = selectedFilter != null,
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.reminders) { reminderWithCustomer ->
                    ReminderItem(
                        reminder = reminderWithCustomer.reminder,
                        customerName = reminderWithCustomer.customerName,
                        description = getReminderDescription(reminderWithCustomer.reminder.type),
                        onClick = { onReminderClick(reminderWithCustomer.reminder.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ReminderFilterChip(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    count: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium
                )
                if (count > 0) {
                    Badge(
                        containerColor = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        },
                        contentColor = if (isSelected) {
                            MaterialTheme.colorScheme.background
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                    ) {
                        Text(
                            text = count.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }
        },
        modifier = modifier,
        leadingIcon = null,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.background,
            labelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
            iconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
            selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            selectedLabelColor = MaterialTheme.colorScheme.primary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.primary
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            selectedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp
        )
    )
}

@Composable
private fun EmptyRemindersState(
    hasFilter: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(40.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.NotificationsNone,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = if (hasFilter) "No reminders in this category" else "No reminders yet",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
            Text(
                text = if (hasFilter) {
                    "Try selecting a different filter"
                } else {
                    "Reminders will appear here when policies are added"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            )
        }
    }
}

private fun getReminderDescription(type: ReminderType): String {
    return when (type) {
        ReminderType.POLICY_RENEWAL -> "Policy renewal"
        ReminderType.BIRTHDAY -> "Birthday"
        ReminderType.ANNIVERSARY -> "Anniversary"
        ReminderType.FOLLOW_UP_CALL -> "Follow-up call"
    }
}
