package com.policyreminder.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.policyreminder.app.data.database.AppDatabase
import com.policyreminder.app.data.repository.CustomerRepository
import com.policyreminder.app.data.repository.PolicyRepository
import com.policyreminder.app.data.repository.ReminderRepository
import com.policyreminder.app.ui.navigation.BottomNavigationBar
import com.policyreminder.app.ui.navigation.Screen
import com.policyreminder.app.ui.screens.*
import com.policyreminder.app.ui.theme.PolicyReminderTheme
import com.policyreminder.app.ui.viewmodel.HomeViewModel
import com.policyreminder.app.ui.viewmodel.CustomerViewModel
import com.policyreminder.app.ui.viewmodel.PolicyViewModel
import com.policyreminder.app.ui.viewmodel.ReminderViewModel
import com.policyreminder.app.utils.IntentUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Initialize database and repositories
        val database = AppDatabase.getDatabase(applicationContext)
        val customerRepository = CustomerRepository(
            database.customerDao(),
            database.familyMemberDao()
        )
        val policyRepository = PolicyRepository(
            database.policyDao(),
            database.renewalHistoryDao()
        )
        val reminderRepository = ReminderRepository(
            database.reminderDao()
        )
        
        setContent {
            PolicyReminderTheme {
                PolicyReminderApp(
                    customerRepository = customerRepository,
                    policyRepository = policyRepository,
                    reminderRepository = reminderRepository
                )
            }
        }
    }
}

@Composable
fun PolicyReminderApp(
    customerRepository: CustomerRepository,
    policyRepository: PolicyRepository,
    reminderRepository: ReminderRepository
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    
    // Initialize ViewModels
    val homeViewModel = HomeViewModel(
        customerRepository = customerRepository,
        policyRepository = policyRepository,
        reminderRepository = reminderRepository
    )
    
    val customerViewModel = CustomerViewModel(
        customerRepository = customerRepository
    )
    
    val policyViewModel = PolicyViewModel(
        policyRepository = policyRepository,
        customerRepository = customerRepository,
        reminderRepository = reminderRepository
    )
    
    val reminderViewModel = ReminderViewModel(
        reminderRepository = reminderRepository,
        customerRepository = customerRepository
    )
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Show bottom navigation on main screens only
            val mainScreenRoutes = listOf(
                Screen.Home.route,
                Screen.Customers.route,
                Screen.Policies.route,
                Screen.Reminders.route,
                Screen.Settings.route
            )
            if (currentRoute in mainScreenRoutes) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            // Pop up to the start destination and save state
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = homeViewModel,
                    onNavigateToAddCustomer = {
                        navController.navigate(Screen.AddCustomer.route)
                    },
                    onNavigateToAddPolicy = {
                        navController.navigate(Screen.AddPolicy.route)
                    },
                    onNavigateToRenewals = {
                        // Navigate to reminders filtered by renewals
                        navController.navigate(Screen.Reminders.route)
                    },
                    onNavigateToBirthdays = {
                        // Navigate to reminders filtered by birthdays
                        navController.navigate(Screen.Reminders.route)
                    },
                    onNavigateToAnniversaries = {
                        // Navigate to reminders filtered by anniversaries
                        navController.navigate(Screen.Reminders.route)
                    },
                    onNavigateToFollowUps = {
                        // Navigate to reminders filtered by follow-ups
                        navController.navigate(Screen.Reminders.route)
                    },
                    onNavigateToCustomers = {
                        navController.navigate(Screen.Customers.route)
                    },
                    onNavigateToPolicies = {
                        navController.navigate(Screen.Policies.route)
                    },
                    onPolicyClick = { policyId ->
                        navController.navigate(Screen.PolicyDetail.createRoute(policyId))
                    },
                    onReminderClick = { reminderId ->
                        navController.navigate(Screen.ReminderDetail.createRoute(reminderId))
                    }
                )
            }
            
            composable(Screen.Customers.route) {
                CustomersScreen(
                    viewModel = customerViewModel,
                    onCustomerClick = { customerId ->
                        navController.navigate(Screen.CustomerDetail.createRoute(customerId))
                    },
                    onAddCustomerClick = {
                        navController.navigate(Screen.AddCustomer.route)
                    }
                )
            }
            
            composable(Screen.Policies.route) {
                PoliciesScreen(
                    viewModel = policyViewModel,
                    onPolicyClick = { policyId ->
                        navController.navigate(Screen.PolicyDetail.createRoute(policyId))
                    },
                    onAddPolicyClick = {
                        navController.navigate(Screen.AddPolicy.route)
                    }
                )
            }
            
            composable(Screen.Reminders.route) {
                RemindersScreen(
                    viewModel = reminderViewModel,
                    onReminderClick = { reminderId ->
                        navController.navigate(Screen.ReminderDetail.createRoute(reminderId))
                    }
                )
            }
            
            composable(Screen.Settings.route) {
                SettingsScreen(
                    onNavigateToMessageTemplates = {
                        // TODO: Navigate to message templates
                    },
                    onNavigateToReminderSettings = {
                        // TODO: Navigate to reminder settings
                    },
                    onNavigateToAbout = {
                        // TODO: Navigate to about screen
                    }
                )
            }
            
            composable(Screen.AddCustomer.route) {
                AddCustomerScreen(
                    viewModel = customerViewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onCustomerAdded = { customerId ->
                        navController.popBackStack()
                        navController.navigate(Screen.CustomerDetail.createRoute(customerId))
                    }
                )
            }
            
            composable(Screen.AddPolicy.route) {
                AddPolicyScreen(
                    policyViewModel = policyViewModel,
                    customerViewModel = customerViewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onPolicyAdded = { policyId ->
                        navController.popBackStack()
                        navController.navigate(Screen.PolicyDetail.createRoute(policyId))
                    }
                )
            }
            
            composable(
                route = Screen.PolicyDetail.route,
                arguments = listOf(navArgument("policyId") { type = NavType.LongType })
            ) { backStackEntry ->
                val policyId = backStackEntry.arguments?.getLong("policyId") ?: return@composable
                PolicyDetailScreen(
                    policyId = policyId,
                    viewModel = policyViewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onEditClick = {
                        // TODO: Navigate to edit policy screen
                    },
                    onCallCustomer = {
                        // TODO: Get customer phone and call
                    }
                )
            }
            
            composable(
                route = Screen.CustomerDetail.route,
                arguments = listOf(navArgument("customerId") { type = NavType.LongType })
            ) { backStackEntry ->
                val customerId = backStackEntry.arguments?.getLong("customerId") ?: return@composable
                CustomerDetailScreen(
                    customerId = customerId,
                    viewModel = customerViewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onEditClick = {
                        // TODO: Navigate to edit customer screen
                    },
                    onCallClick = { phoneNumber ->
                        IntentUtils.makePhoneCall(context, phoneNumber)
                    },
                    onWhatsAppClick = { phoneNumber ->
                        IntentUtils.openWhatsApp(context, phoneNumber)
                    }
                )
            }
            
            composable(
                route = Screen.ReminderDetail.route,
                arguments = listOf(navArgument("reminderId") { type = NavType.LongType })
            ) {
                // TODO: Implement Reminder Detail Screen
                PlaceholderScreen(title = "Reminder Detail")
            }
        }
    }
}

@Composable
fun PlaceholderScreen(title: String) {
    androidx.compose.foundation.layout.Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        androidx.compose.material3.Text(
            text = "$title - Coming Soon",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
        )
    }
}
