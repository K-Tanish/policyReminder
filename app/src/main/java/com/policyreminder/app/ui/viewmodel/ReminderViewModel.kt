package com.policyreminder.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.policyreminder.app.data.model.Reminder
import com.policyreminder.app.data.model.ReminderType
import com.policyreminder.app.data.repository.CustomerRepository
import com.policyreminder.app.data.repository.ReminderRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ReminderUiState(
    val reminders: List<ReminderWithCustomerName> = emptyList(),
    val filterType: ReminderType? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

data class ReminderWithCustomerName(
    val reminder: Reminder,
    val customerName: String
)

class ReminderViewModel(
    private val reminderRepository: ReminderRepository,
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReminderUiState())
    val uiState: StateFlow<ReminderUiState> = _uiState.asStateFlow()

    private val _filterType = MutableStateFlow<ReminderType?>(null)

    init {
        loadReminders()
    }

    private fun loadReminders() {
        viewModelScope.launch {
            combine(
                _filterType,
                reminderRepository.getAllActiveReminders(),
                customerRepository.getAllCustomers()
            ) { filterType, reminders, customers ->
                val customerMap = customers.associateBy { it.id }
                
                val filteredReminders = if (filterType != null) {
                    reminders.filter { it.type == filterType }
                } else {
                    reminders
                }
                
                val remindersWithNames = filteredReminders.mapNotNull { reminder ->
                    customerMap[reminder.customerId]?.let { customer ->
                        ReminderWithCustomerName(
                            reminder = reminder,
                            customerName = customer.getFullName()
                        )
                    }
                }
                
                ReminderUiState(
                    reminders = remindersWithNames,
                    filterType = filterType,
                    isLoading = false
                )
            }.catch { e ->
                emit(_uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message
                ))
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun filterByType(type: ReminderType?) {
        _filterType.value = type
    }

    fun completeReminder(reminder: Reminder, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val updatedReminder = reminder.copy(isCompleted = true)
                reminderRepository.updateReminder(updatedReminder)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun snoozeReminder(reminder: Reminder, snoozeUntil: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val updatedReminder = reminder.copy(
                    isSnoozed = true,
                    snoozeUntil = snoozeUntil
                )
                reminderRepository.updateReminder(updatedReminder)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun deleteReminder(reminder: Reminder, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                reminderRepository.deleteReminder(reminder)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun getRemindersByType(type: ReminderType): Flow<List<ReminderWithCustomerName>> {
        return combine(
            reminderRepository.getRemindersByType(type),
            customerRepository.getAllCustomers()
        ) { reminders, customers ->
            val customerMap = customers.associateBy { it.id }
            reminders.mapNotNull { reminder ->
                customerMap[reminder.customerId]?.let { customer ->
                    ReminderWithCustomerName(
                        reminder = reminder,
                        customerName = customer.getFullName()
                    )
                }
            }
        }
    }
}
