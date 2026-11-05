package com.policyreminder.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.policyreminder.app.data.model.Policy
import com.policyreminder.app.data.model.PolicyStatus
import com.policyreminder.app.data.model.RenewalHistory
import com.policyreminder.app.data.repository.CustomerRepository
import com.policyreminder.app.data.repository.PolicyRepository
import com.policyreminder.app.data.repository.ReminderRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class PolicyUiState(
    val policies: List<PolicyWithCustomerName> = emptyList(),
    val isLoading: Boolean = true,
    val searchQuery: String = "",
    val filterStatus: PolicyStatus? = null,
    val errorMessage: String? = null
)

data class PolicyWithCustomerName(
    val policy: Policy,
    val customerName: String,
    val customerInitials: String
)

data class PolicyDetailUiState(
    val policy: Policy? = null,
    val customerName: String = "",
    val renewalHistory: List<RenewalHistory> = emptyList(),
    val isLoading: Boolean = true
)

class PolicyViewModel(
    private val policyRepository: PolicyRepository,
    private val customerRepository: CustomerRepository,
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PolicyUiState())
    val uiState: StateFlow<PolicyUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    private val _filterStatus = MutableStateFlow<PolicyStatus?>(null)

    init {
        loadPolicies()
    }

    private fun loadPolicies() {
        viewModelScope.launch {
            combine(
                _searchQuery.debounce(300),
                _filterStatus,
                policyRepository.getAllPolicies(),
                customerRepository.getAllCustomers()
            ) { query, status, policies, customers ->
                val customerMap = customers.associateBy { it.id }
                
                var filteredPolicies = policies
                
                if (query.isNotEmpty()) {
                    filteredPolicies = filteredPolicies.filter { policy ->
                        policy.policyNumber.contains(query, ignoreCase = true) ||
                        policy.insuranceCompany.contains(query, ignoreCase = true) ||
                        policy.policyType.contains(query, ignoreCase = true) ||
                        policy.policyholderName.contains(query, ignoreCase = true)
                    }
                }
                
                if (status != null) {
                    filteredPolicies = filteredPolicies.filter { it.getCurrentStatus() == status }
                }
                
                val policiesWithCustomer = filteredPolicies.mapNotNull { policy ->
                    customerMap[policy.customerId]?.let { customer ->
                        PolicyWithCustomerName(
                            policy = policy,
                            customerName = customer.getFullName(),
                            customerInitials = customer.getInitials()
                        )
                    }
                }
                
                PolicyUiState(
                    policies = policiesWithCustomer,
                    isLoading = false,
                    searchQuery = query,
                    filterStatus = status
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

    fun searchPolicies(query: String) {
        _searchQuery.value = query
    }

    fun filterByStatus(status: PolicyStatus?) {
        _filterStatus.value = status
    }

    fun addPolicy(policy: Policy, onSuccess: (Long) -> Unit) {
        viewModelScope.launch {
            try {
                val policyId = policyRepository.insertPolicy(policy)
                // Create renewal reminders here if needed
                onSuccess(policyId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun updatePolicy(policy: Policy, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                policyRepository.updatePolicy(policy)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun deletePolicy(policy: Policy, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                policyRepository.deletePolicy(policy)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun renewPolicy(
        policy: Policy,
        newRenewalDate: Long,
        newPremium: Double,
        remarks: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Create renewal history
                val history = RenewalHistory(
                    policyId = policy.id,
                    previousRenewalDate = policy.renewalDate,
                    newRenewalDate = newRenewalDate,
                    previousPremium = policy.premiumAmount,
                    newPremium = newPremium,
                    remarks = remarks
                )
                policyRepository.insertRenewalHistory(history)
                
                // Update policy
                val updatedPolicy = policy.copy(
                    renewalDate = newRenewalDate,
                    premiumAmount = newPremium,
                    status = PolicyStatus.RENEWED,
                    updatedAt = System.currentTimeMillis()
                )
                policyRepository.updatePolicy(updatedPolicy)
                
                // Delete old renewal reminders
                reminderRepository.deleteRenewalRemindersByPolicyId(policy.id)
                
                // Create new reminders would go here
                
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun getPolicyDetail(policyId: Long): Flow<PolicyDetailUiState> {
        return combine(
            policyRepository.getPolicyById(policyId),
            policyRepository.getRenewalHistoryByPolicyId(policyId),
            customerRepository.getAllCustomers()
        ) { policy, history, customers ->
            val customerName = policy?.let { p ->
                customers.find { it.id == p.customerId }?.getFullName() ?: ""
            } ?: ""
            
            PolicyDetailUiState(
                policy = policy,
                customerName = customerName,
                renewalHistory = history,
                isLoading = false
            )
        }
    }
}
