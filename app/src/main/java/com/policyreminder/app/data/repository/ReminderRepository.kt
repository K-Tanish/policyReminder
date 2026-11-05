package com.policyreminder.app.data.repository

import com.policyreminder.app.data.dao.ReminderDao
import com.policyreminder.app.data.model.Reminder
import com.policyreminder.app.data.model.ReminderType
import kotlinx.coroutines.flow.Flow

class ReminderRepository(
    private val reminderDao: ReminderDao
) {
    
    fun getAllActiveReminders(): Flow<List<Reminder>> = reminderDao.getAllActiveReminders()
    
    fun getReminderById(reminderId: Long): Flow<Reminder?> = reminderDao.getReminderById(reminderId)
    
    fun getRemindersByCustomerId(customerId: Long): Flow<List<Reminder>> = 
        reminderDao.getRemindersByCustomerId(customerId)
    
    fun getRemindersByPolicyId(policyId: Long): Flow<List<Reminder>> = 
        reminderDao.getRemindersByPolicyId(policyId)
    
    fun getRemindersByType(type: ReminderType): Flow<List<Reminder>> = 
        reminderDao.getRemindersByType(type)
    
    fun getRemindersInDateRange(startDate: Long, endDate: Long): Flow<List<Reminder>> = 
        reminderDao.getRemindersInDateRange(startDate, endDate)
    
    fun getUpcomingReminders(date: Long): Flow<List<Reminder>> = 
        reminderDao.getUpcomingReminders(date)
    
    fun getActiveReminderCount(): Flow<Int> = reminderDao.getActiveReminderCount()
    
    suspend fun insertReminder(reminder: Reminder): Long = reminderDao.insertReminder(reminder)
    
    suspend fun updateReminder(reminder: Reminder) = reminderDao.updateReminder(reminder)
    
    suspend fun deleteReminder(reminder: Reminder) = reminderDao.deleteReminder(reminder)
    
    suspend fun deleteReminderById(reminderId: Long) = reminderDao.deleteReminderById(reminderId)
    
    suspend fun deleteRenewalRemindersByPolicyId(policyId: Long) = 
        reminderDao.deleteRenewalRemindersByPolicyId(policyId)
}
