package com.policyreminder.app.data.dao

import androidx.room.*
import com.policyreminder.app.data.model.RenewalHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface RenewalHistoryDao {
    
    @Query("SELECT * FROM renewal_history WHERE policyId = :policyId ORDER BY renewedAt DESC")
    fun getRenewalHistoryByPolicyId(policyId: Long): Flow<List<RenewalHistory>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRenewalHistory(history: RenewalHistory): Long
    
    @Query("DELETE FROM renewal_history WHERE policyId = :policyId")
    suspend fun deleteRenewalHistoryByPolicyId(policyId: Long)
}
