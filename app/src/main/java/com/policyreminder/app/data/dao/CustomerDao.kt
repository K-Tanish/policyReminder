package com.policyreminder.app.data.dao

import androidx.room.*
import com.policyreminder.app.data.model.Customer
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {
    
    @Query("SELECT * FROM customers ORDER BY firstName ASC")
    fun getAllCustomers(): Flow<List<Customer>>
    
    @Query("SELECT * FROM customers WHERE id = :customerId")
    fun getCustomerById(customerId: Long): Flow<Customer?>
    
    @Query("""
        SELECT * FROM customers 
        WHERE firstName LIKE '%' || :query || '%' 
        OR surname LIKE '%' || :query || '%'
        OR mobileNumber LIKE '%' || :query || '%'
        ORDER BY firstName ASC
    """)
    fun searchCustomers(query: String): Flow<List<Customer>>
    
    @Query("SELECT COUNT(*) FROM customers")
    fun getCustomerCount(): Flow<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: Customer): Long
    
    @Update
    suspend fun updateCustomer(customer: Customer)
    
    @Delete
    suspend fun deleteCustomer(customer: Customer)
    
    @Query("DELETE FROM customers WHERE id = :customerId")
    suspend fun deleteCustomerById(customerId: Long)
}
