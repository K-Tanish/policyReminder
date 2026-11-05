package com.policyreminder.app.data.repository

import com.policyreminder.app.data.dao.CustomerDao
import com.policyreminder.app.data.dao.FamilyMemberDao
import com.policyreminder.app.data.model.Customer
import com.policyreminder.app.data.model.FamilyMember
import kotlinx.coroutines.flow.Flow

class CustomerRepository(
    private val customerDao: CustomerDao,
    private val familyMemberDao: FamilyMemberDao
) {
    
    fun getAllCustomers(): Flow<List<Customer>> = customerDao.getAllCustomers()
    
    fun getCustomerById(customerId: Long): Flow<Customer?> = customerDao.getCustomerById(customerId)
    
    fun searchCustomers(query: String): Flow<List<Customer>> = customerDao.searchCustomers(query)
    
    fun getCustomerCount(): Flow<Int> = customerDao.getCustomerCount()
    
    fun getFamilyMembersByCustomerId(customerId: Long): Flow<List<FamilyMember>> = 
        familyMemberDao.getFamilyMembersByCustomerId(customerId)
    
    fun getAllFamilyMembersWithBirthdays(): Flow<List<FamilyMember>> = 
        familyMemberDao.getAllFamilyMembersWithBirthdays()
    
    fun getAllFamilyMembersWithAnniversaries(): Flow<List<FamilyMember>> = 
        familyMemberDao.getAllFamilyMembersWithAnniversaries()
    
    suspend fun insertCustomer(customer: Customer): Long = customerDao.insertCustomer(customer)
    
    suspend fun updateCustomer(customer: Customer) = customerDao.updateCustomer(customer)
    
    suspend fun deleteCustomer(customer: Customer) = customerDao.deleteCustomer(customer)
    
    suspend fun deleteCustomerById(customerId: Long) = customerDao.deleteCustomerById(customerId)
    
    suspend fun insertFamilyMember(member: FamilyMember): Long = familyMemberDao.insertFamilyMember(member)
    
    suspend fun updateFamilyMember(member: FamilyMember) = familyMemberDao.updateFamilyMember(member)
    
    suspend fun deleteFamilyMember(member: FamilyMember) = familyMemberDao.deleteFamilyMember(member)
}
