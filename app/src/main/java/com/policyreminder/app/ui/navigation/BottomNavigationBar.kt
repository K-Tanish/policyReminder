package com.policyreminder.app.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.policyreminder.app.ui.theme.Foreground15

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem(Screen.Home.route, Icons.Outlined.Home, "Home"),
        BottomNavItem(Screen.Customers.route, Icons.Outlined.People, "Customers"),
        BottomNavItem(Screen.Policies.route, Icons.Outlined.Description, "Policies"),
        BottomNavItem(Screen.Reminders.route, Icons.Outlined.Notifications, "Reminders"),
        BottomNavItem(Screen.Settings.route, Icons.Outlined.Settings, "Settings")
    )
    
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    Box(
                        modifier = if (isSelected) {
                            Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Foreground15)
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        } else {
                            Modifier.padding(4.dp)
                        },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                            }
                        )
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        }
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}
