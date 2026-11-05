package com.policyreminder.app.data.repository

import com.policyreminder.app.data.dao.PolicyDao
import com.policyreminder.app.data.dao.RenewalHistoryDao
import com.policyreminder.app.data.model.Policy
import com.policyreminder.app.data.model.PolicyStatus
import com.policyreminder.app.data.model.RenewalHistory
import kotlinx.coroutines.flow.Flow

class PolicyRepository(
    private val policyDao: PolicyDao,
    private val renewalHistoryDao: RenewalHistoryDao
) {
    
    fun getAllPolicies(): Flow<List<Policy>> = policyDao.getAllPolicies()
    
    fun getPolicyById(policyId: Long): Flow<Policy?> = policyDao.getPolicyById(policyId)
    
    fun getPoliciesByCustomerId(customerId: Long): Flow<List<Policy>> = 
        policyDao.getPoliciesByCustomerId(customerId)
    
    fun searchPolicies(query: String): Flow<List<Policy>> = policyDao.searchPolicies(query)
    
    fun getPoliciesByStatus(status: PolicyStatus): Flow<List<Policy>> = 
        policyDao.getPoliciesByStatus(status)
    
    fun getPoliciesByDateRange(startDate: Long, endDate: Long): Flow<List<Policy>> = 
        policyDao.getPoliciesByDateRange(startDate, endDate)
    
    fun getPolicyCount(): Flow<Int> = policyDao.getPolicyCount()
    
    fun getPolicyCountByCustomer(customerId: Long): Flow<Int> = 
        policyDao.getPolicyCountByCustomer(customerId)
    
    fun getRenewalHistoryByPolicyId(policyId: Long): Flow<List<RenewalHistory>> = 
        renewalHistoryDao.getRenewalHistoryByPolicyId(policyId)
    
    suspend fun insertPolicy(policy: Policy): Long = policyDao.insertPolicy(policy)
    
    suspend fun updatePolicy(policy: Policy) = policyDao.updatePolicy(policy)
    
    suspend fun deletePolicy(policy: Policy) = policyDao.deletePolicy(policy)
    
    suspend fun deletePolicyById(policyId: Long) = policyDao.deletePolicyById(policyId)
    
    suspend fun insertRenewalHistory(history: RenewalHistory): Long = 
        renewalHistoryDao.insertRenewalHistory(history)
}
