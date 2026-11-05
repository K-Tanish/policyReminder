package com.policyreminder.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.policyreminder.app.data.model.Customer
import com.policyreminder.app.data.model.FamilyMember
import com.policyreminder.app.data.repository.CustomerRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CustomerUiState(
    val customers: List<Customer> = emptyList(),
    val isLoading: Boolean = true,
    val searchQuery: String = "",
    val errorMessage: String? = null
)

data class CustomerDetailUiState(
    val customer: Customer? = null,
    val familyMembers: List<FamilyMember> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class CustomerViewModel(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CustomerUiState())
    val uiState: StateFlow<CustomerUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    
    init {
        loadCustomers()
    }

    private fun loadCustomers() {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .flatMapLatest { query ->
                    if (query.isEmpty()) {
                        customerRepository.getAllCustomers()
                    } else {
                        customerRepository.searchCustomers(query)
                    }
                }
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
                .collect { customers ->
                    _uiState.value = _uiState.value.copy(
                        customers = customers,
                        isLoading = false,
                        errorMessage = null
                    )
                }
        }
    }

    fun searchCustomers(query: String) {
        _searchQuery.value = query
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun addCustomer(customer: Customer, onSuccess: (Long) -> Unit) {
        viewModelScope.launch {
            try {
                val id = customerRepository.insertCustomer(customer)
                onSuccess(id)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun updateCustomer(customer: Customer, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                customerRepository.updateCustomer(customer)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun deleteCustomer(customer: Customer, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                customerRepository.deleteCustomer(customer)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun getCustomerWithFamily(customerId: Long): Flow<CustomerDetailUiState> {
        return combine(
            customerRepository.getCustomerById(customerId),
            customerRepository.getFamilyMembersByCustomerId(customerId)
        ) { customer, familyMembers ->
            CustomerDetailUiState(
                customer = customer,
                familyMembers = familyMembers,
                isLoading = false
            )
        }.catch { e ->
            emit(CustomerDetailUiState(
                isLoading = false,
                errorMessage = e.message
            ))
        }
    }

    fun addFamilyMember(member: FamilyMember, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                customerRepository.insertFamilyMember(member)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }

    fun deleteFamilyMember(member: FamilyMember, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                customerRepository.deleteFamilyMember(member)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message)
            }
        }
    }
}
