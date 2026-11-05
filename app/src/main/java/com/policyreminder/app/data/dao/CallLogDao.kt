package com.policyreminder.app.data.dao

import androidx.room.*
import com.policyreminder.app.data.model.CallLog
import com.policyreminder.app.data.model.CallOutcome
import kotlinx.coroutines.flow.Flow

@Dao
interface CallLogDao {
    
    @Query("SELECT * FROM call_logs ORDER BY callDate DESC")
    fun getAllCallLogs(): Flow<List<CallLog>>
    
    @Query("SELECT * FROM call_logs WHERE id = :logId")
    fun getCallLogById(logId: Long): Flow<CallLog?>
    
    @Query("SELECT * FROM call_logs WHERE customerId = :customerId ORDER BY callDate DESC")
    fun getCallLogsByCustomerId(customerId: Long): Flow<List<CallLog>>
    
    @Query("SELECT * FROM call_logs WHERE policyId = :policyId ORDER BY callDate DESC")
    fun getCallLogsByPolicyId(policyId: Long): Flow<List<CallLog>>
    
    @Query("SELECT * FROM call_logs WHERE outcome = :outcome ORDER BY callDate DESC")
    fun getCallLogsByOutcome(outcome: CallOutcome): Flow<List<CallLog>>
    
    @Query("""
        SELECT * FROM call_logs 
        WHERE followUpDate IS NOT NULL 
        AND followUpDate <= :date 
        ORDER BY followUpDate ASC
    """)
    fun getCallLogsWithUpcomingFollowUp(date: Long): Flow<List<CallLog>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCallLog(callLog: CallLog): Long
    
    @Update
    suspend fun updateCallLog(callLog: CallLog)
    
    @Delete
    suspend fun deleteCallLog(callLog: CallLog)
}
