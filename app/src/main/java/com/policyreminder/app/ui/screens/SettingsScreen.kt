package com.policyreminder.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.policyreminder.app.ui.theme.Foreground15

@Composable
fun SettingsScreen(
    onNavigateToMessageTemplates: () -> Unit,
    onNavigateToReminderSettings: () -> Unit,
    onNavigateToAbout: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "TP",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Text(
                    text = "Tanish Patil",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = "Insurance Advisor",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                )
            }
        }
        
        // General Section
        item {
            Text(
                text = "General",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        item {
            SettingsCard {
                SettingsItem(
                    icon = Icons.Outlined.Person,
                    title = "Profile",
                    subtitle = "Edit your profile information",
                    onClick = { /* TODO */ }
                )
                Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                SettingsItem(
                    icon = Icons.Outlined.Message,
                    title = "Message Templates",
                    subtitle = "Customize WhatsApp messages",
                    onClick = onNavigateToMessageTemplates
                )
            }
        }
        
        // Reminders Section
        item {
            Text(
                text = "Reminders",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        item {
            SettingsCard {
                SettingsItem(
                    icon = Icons.Outlined.Notifications,
                    title = "Reminder Settings",
                    subtitle = "Configure reminder intervals",
                    onClick = onNavigateToReminderSettings
                )
                Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                SettingsItem(
                    icon = Icons.Outlined.Schedule,
                    title = "Notification Time",
                    subtitle = "Set preferred notification time",
                    onClick = { /* TODO */ }
                )
            }
        }
        
        // Data & Storage
        item {
            Text(
                text = "Data & Storage",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        item {
            SettingsCard {
                SettingsItem(
                    icon = Icons.Outlined.CloudUpload,
                    title = "Backup Data",
                    subtitle = "Export your data",
                    onClick = { /* TODO */ }
                )
                Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                SettingsItem(
                    icon = Icons.Outlined.CloudDownload,
                    title = "Restore Data",
                    subtitle = "Import from backup",
                    onClick = { /* TODO */ }
                )
            }
        }
        
        // About Section
        item {
            Text(
                text = "About",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        
        item {
            SettingsCard {
                SettingsItem(
                    icon = Icons.Outlined.Info,
                    title = "About Policy Reminder",
                    subtitle = "Version 1.0.0",
                    onClick = onNavigateToAbout
                )
                Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                SettingsItem(
                    icon = Icons.Outlined.Share,
                    title = "Share App",
                    subtitle = "Recommend to colleagues",
                    onClick = { /* TODO */ }
                )
                Divider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                SettingsItem(
                    icon = Icons.Outlined.PrivacyTip,
                    title = "Privacy Policy",
                    subtitle = "How we handle your data",
                    onClick = { /* TODO */ }
                )
            }
        }
    }
}

@Composable
private fun SettingsCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Foreground15
        )
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            content = content
        )
    }
}

@Composable
private fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
            modifier = Modifier.size(24.dp)
        )
        
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
        }
        
        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = "Go",
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
            modifier = Modifier.size(20.dp)
        )
    }
}
