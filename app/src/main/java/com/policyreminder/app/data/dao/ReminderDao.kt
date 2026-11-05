package com.policyreminder.app.data.dao

import androidx.room.*
import com.policyreminder.app.data.model.Reminder
import com.policyreminder.app.data.model.ReminderType
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    
    @Query("SELECT * FROM reminders WHERE isCompleted = 0 ORDER BY reminderDate ASC")
    fun getAllActiveReminders(): Flow<List<Reminder>>
    
    @Query("SELECT * FROM reminders WHERE id = :reminderId")
    fun getReminderById(reminderId: Long): Flow<Reminder?>
    
    @Query("SELECT * FROM reminders WHERE customerId = :customerId ORDER BY reminderDate ASC")
    fun getRemindersByCustomerId(customerId: Long): Flow<List<Reminder>>
    
    @Query("SELECT * FROM reminders WHERE policyId = :policyId ORDER BY reminderDate ASC")
    fun getRemindersByPolicyId(policyId: Long): Flow<List<Reminder>>
    
    @Query("SELECT * FROM reminders WHERE type = :type AND isCompleted = 0 ORDER BY reminderDate ASC")
    fun getRemindersByType(type: ReminderType): Flow<List<Reminder>>
    
    @Query("""
        SELECT * FROM reminders 
        WHERE reminderDate BETWEEN :startDate AND :endDate 
        AND isCompleted = 0
        ORDER BY reminderDate ASC
    """)
    fun getRemindersInDateRange(startDate: Long, endDate: Long): Flow<List<Reminder>>
    
    @Query("""
        SELECT * FROM reminders 
        WHERE reminderDate <= :date 
        AND isCompleted = 0
        AND (isSnoozed = 0 OR snoozeUntil <= :date)
        ORDER BY reminderDate ASC
    """)
    fun getUpcomingReminders(date: Long): Flow<List<Reminder>>
    
    @Query("SELECT COUNT(*) FROM reminders WHERE isCompleted = 0")
    fun getActiveReminderCount(): Flow<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: Reminder): Long
    
    @Update
    suspend fun updateReminder(reminder: Reminder)
    
    @Delete
    suspend fun deleteReminder(reminder: Reminder)
    
    @Query("DELETE FROM reminders WHERE id = :reminderId")
    suspend fun deleteReminderById(reminderId: Long)
    
    @Query("DELETE FROM reminders WHERE policyId = :policyId AND type = 'POLICY_RENEWAL'")
    suspend fun deleteRenewalRemindersByPolicyId(policyId: Long)
}
