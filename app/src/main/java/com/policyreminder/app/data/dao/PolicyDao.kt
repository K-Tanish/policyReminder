package com.policyreminder.app.data.dao

import androidx.room.*
import com.policyreminder.app.data.model.Policy
import com.policyreminder.app.data.model.PolicyStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface PolicyDao {
    
    @Query("SELECT * FROM policies ORDER BY renewalDate ASC")
    fun getAllPolicies(): Flow<List<Policy>>
    
    @Query("SELECT * FROM policies WHERE id = :policyId")
    fun getPolicyById(policyId: Long): Flow<Policy?>
    
    @Query("SELECT * FROM policies WHERE customerId = :customerId ORDER BY renewalDate ASC")
    fun getPoliciesByCustomerId(customerId: Long): Flow<List<Policy>>
    
    @Query("""
        SELECT * FROM policies 
        WHERE policyNumber LIKE '%' || :query || '%'
        OR insuranceCompany LIKE '%' || :query || '%'
        OR policyType LIKE '%' || :query || '%'
        OR policyholderName LIKE '%' || :query || '%'
        ORDER BY renewalDate ASC
    """)
    fun searchPolicies(query: String): Flow<List<Policy>>
    
    @Query("SELECT * FROM policies WHERE status = :status ORDER BY renewalDate ASC")
    fun getPoliciesByStatus(status: PolicyStatus): Flow<List<Policy>>
    
    @Query("""
        SELECT * FROM policies 
        WHERE renewalDate BETWEEN :startDate AND :endDate 
        ORDER BY renewalDate ASC
    """)
    fun getPoliciesByDateRange(startDate: Long, endDate: Long): Flow<List<Policy>>
    
    @Query("SELECT COUNT(*) FROM policies")
    fun getPolicyCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM policies WHERE customerId = :customerId")
    fun getPolicyCountByCustomer(customerId: Long): Flow<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPolicy(policy: Policy): Long
    
    @Update
    suspend fun updatePolicy(policy: Policy)
    
    @Delete
    suspend fun deletePolicy(policy: Policy)
    
    @Query("DELETE FROM policies WHERE id = :policyId")
    suspend fun deletePolicyById(policyId: Long)
}
