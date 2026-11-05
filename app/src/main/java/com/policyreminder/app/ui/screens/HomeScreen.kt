package com.policyreminder.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.policyreminder.app.ui.components.PolicyCard
import com.policyreminder.app.ui.components.QuickAccessButton
import com.policyreminder.app.ui.components.QuickActionCard
import com.policyreminder.app.ui.components.ReminderItem
import com.policyreminder.app.ui.components.RenewalCalendar
import com.policyreminder.app.ui.components.RenewalDate
import com.policyreminder.app.ui.theme.Background30
import com.policyreminder.app.ui.theme.Foreground15
import com.policyreminder.app.ui.viewmodel.HomeViewModel
import java.util.Calendar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToAddCustomer: () -> Unit,
    onNavigateToAddPolicy: () -> Unit,
    onNavigateToRenewals: () -> Unit,
    onNavigateToBirthdays: () -> Unit,
    onNavigateToAnniversaries: () -> Unit,
    onNavigateToFollowUps: () -> Unit,
    onNavigateToCustomers: () -> Unit,
    onNavigateToPolicies: () -> Unit,
    onPolicyClick: (Long) -> Unit,
    onReminderClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Top Profile Header
        item {
            TopProfileHeader(
                greeting = viewModel.getGreeting(),
                agentName = "Tanish Patil",
                role = "Insurance Advisor",
                notificationCount = uiState.activeReminderCount,
                onSearchClick = { /* TODO */ },
                onNotificationClick = { /* TODO */ }
            )
        }
        
        // Quick Action Cards
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                QuickActionCard(
                    icon = Icons.Outlined.PersonAdd,
                    title = "Add Customer",
                    subtitle = "Register a new client",
                    onClick = onNavigateToAddCustomer,
                    modifier = Modifier.weight(1f)
                )
                QuickActionCard(
                    icon = Icons.Outlined.NoteAdd,
                    title = "Add Policy",
                    subtitle = "Register a new policy",
                    onClick = onNavigateToAddPolicy,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        
        // Quick Access Section
        item {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Quick Access",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    QuickAccessButton(
                        icon = Icons.Outlined.Refresh,
                        label = "Renewals",
                        onClick = onNavigateToRenewals
                    )
                    QuickAccessButton(
                        icon = Icons.Outlined.Cake,
                        label = "Birthdays",
                        onClick = onNavigateToBirthdays
                    )
                    QuickAccessButton(
                        icon = Icons.Outlined.Favorite,
                        label = "Anniversaries",
                        onClick = onNavigateToAnniversaries
                    )
                    QuickAccessButton(
                        icon = Icons.Outlined.Phone,
                        label = "Follow-up",
                        onClick = onNavigateToFollowUps
                    )
                }
            }
        }
        
        // Renewal Calendar Section
        item {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Renewal Calendar",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                // Convert policies to renewal dates for the calendar
                val renewalDates = uiState.recentPolicies
                    .groupBy { policy ->
                        Calendar.getInstance().apply {
                            timeInMillis = policy.policy.renewalDate
                            set(Calendar.HOUR_OF_DAY, 0)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)
                        }
                    }
                    .map { (date, policies) ->
                        RenewalDate(
                            date = date,
                            count = policies.size,
                            policyIds = policies.map { it.policy.id }
                        )
                    }
                
                RenewalCalendar(
                    renewalDates = renewalDates,
                    onDateClick = { date ->
                        // Navigate to policies filtered by this date
                        onNavigateToPolicies()
                    }
                )
            }
        }
        
        // Customer Management Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Foreground15)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable(onClick = onNavigateToCustomers)
                    .padding(20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.People,
                            contentDescription = "Customers",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Explore Customers",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "View and manage your client database",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                    }
                    
                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = "Go",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        
        // My Policies Section
        item {
            Column(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Policies",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "See all",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                        modifier = Modifier.clickable(onClick = onNavigateToPolicies)
                    )
                }
                
                if (uiState.recentPolicies.isNotEmpty()) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.recentPolicies) { policyWithCustomer ->
                            PolicyCard(
                                policy = policyWithCustomer.policy,
                                customerName = policyWithCustomer.customerName,
                                customerInitials = policyWithCustomer.customerInitials,
                                onClick = { onPolicyClick(policyWithCustomer.policy.id) }
                            )
                        }
                    }
                } else {
                    EmptyStateMessage(
                        message = "No policies yet",
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }
        }
        
        // Upcoming Reminders Section
        item {
            Column(
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Upcoming Reminders",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                
                if (uiState.upcomingReminders.isNotEmpty()) {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        uiState.upcomingReminders.take(3).forEach { reminderWithDetails ->
                            ReminderItem(
                                reminder = reminderWithDetails.reminder,
                                customerName = reminderWithDetails.customerName,
                                description = reminderWithDetails.description,
                                onClick = { onReminderClick(reminderWithDetails.reminder.id) }
                            )
                        }
                    }
                } else {
                    EmptyStateMessage(
                        message = "No upcoming reminders",
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TopProfileHeader(
    greeting: String,
    agentName: String,
    role: String,
    notificationCount: Int,
    onSearchClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Foreground15)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "TP",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Column {
                Text(
                    text = greeting,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                )
                Text(
                    text = agentName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = role,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
            }
        }
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                )
            }
            
            Box {
                IconButton(onClick = onNotificationClick) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                }
                if (notificationCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .align(Alignment.TopEnd)
                            .offset(x = (-4).dp, y = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (notificationCount > 9) "9+" else notificationCount.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyStateMessage(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        )
    }
}
