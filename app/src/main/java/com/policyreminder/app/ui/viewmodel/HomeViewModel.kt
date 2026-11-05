package com.policyreminder.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.policyreminder.app.data.model.*
import com.policyreminder.app.data.repository.CustomerRepository
import com.policyreminder.app.data.repository.PolicyRepository
import com.policyreminder.app.data.repository.ReminderRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

data class HomeUiState(
    val customerCount: Int = 0,
    val policyCount: Int = 0,
    val activeReminderCount: Int = 0,
    val recentPolicies: List<PolicyWithCustomer> = emptyList(),
    val upcomingReminders: List<ReminderWithDetails> = emptyList(),
    val isLoading: Boolean = true
)

data class PolicyWithCustomer(
    val policy: Policy,
    val customerName: String,
    val customerInitials: String
)

data class ReminderWithDetails(
    val reminder: Reminder,
    val customerName: String,
    val description: String
)

class HomeViewModel(
    private val customerRepository: CustomerRepository,
    private val policyRepository: PolicyRepository,
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            combine(
                customerRepository.getCustomerCount(),
                policyRepository.getPolicyCount(),
                reminderRepository.getActiveReminderCount(),
                policyRepository.getAllPolicies(),
                reminderRepository.getUpcomingReminders(getNextMonthTimestamp()),
                customerRepository.getAllCustomers()
            ) { flows: Array<Any?> ->
                val customerCount = flows[0] as Int
                val policyCount = flows[1] as Int
                val reminderCount = flows[2] as Int
                val policies = flows[3] as List<Policy>
                val reminders = flows[4] as List<Reminder>
                val customers = flows[5] as List<Customer>
                
                val customerMap = customers.associateBy { it.id }
                
                val policiesWithCustomer = policies.take(10).mapNotNull { policy ->
                    customerMap[policy.customerId]?.let { customer ->
                        PolicyWithCustomer(
                            policy = policy,
                            customerName = customer.getFullName(),
                            customerInitials = customer.getInitials()
                        )
                    }
                }
                
                val remindersWithDetails = reminders.take(10).mapNotNull { reminder ->
                    customerMap[reminder.customerId]?.let { customer ->
                        ReminderWithDetails(
                            reminder = reminder,
                            customerName = customer.getFullName(),
                            description = formatReminderDescription(reminder)
                        )
                    }
                }
                
                HomeUiState(
                    customerCount = customerCount,
                    policyCount = policyCount,
                    activeReminderCount = reminderCount,
                    recentPolicies = policiesWithCustomer,
                    upcomingReminders = remindersWithDetails,
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun getGreeting(): String {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }

    private fun formatReminderDescription(reminder: Reminder): String {
        return when (reminder.type) {
            ReminderType.POLICY_RENEWAL -> "Policy renewal"
            ReminderType.BIRTHDAY -> "Birthday"
            ReminderType.ANNIVERSARY -> "Anniversary"
            ReminderType.FOLLOW_UP_CALL -> "Follow-up call"
        }
    }

    private fun getNextMonthTimestamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 1)
        return calendar.timeInMillis
    }

    fun refresh() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        loadHomeData()
    }
}
