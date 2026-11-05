package com.policyreminder.app.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.policyreminder.app.data.database.AppDatabase
import com.policyreminder.app.data.model.Reminder
import com.policyreminder.app.data.model.ReminderType
import com.policyreminder.app.utils.NotificationHelper
import kotlinx.coroutines.flow.first
import java.util.Calendar

/**
 * Background worker that checks for due reminders and shows notifications
 */
class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val database = AppDatabase.getDatabase(applicationContext)
            val reminderDao = database.reminderDao()
            val customerDao = database.customerDao()
            val policyDao = database.policyDao()
            
            // Get current time + next 24 hours
            val now = System.currentTimeMillis()
            val tomorrow = now + (24 * 60 * 60 * 1000)
            
            // Get reminders due in next 24 hours
            val upcomingReminders = reminderDao.getRemindersInDateRange(now, tomorrow).first()
            
            // Process each reminder
            upcomingReminders.forEach { reminder ->
                if (!reminder.isCompleted && (!reminder.isSnoozed || (reminder.snoozeUntil ?: 0) <= now)) {
                    val customer = customerDao.getCustomerById(reminder.customerId).first()
                    
                    customer?.let {
                        val policy = reminder.policyId?.let { policyId ->
                            policyDao.getPolicyById(policyId).first()
                        }
                        
                        // Show notification
                        NotificationHelper.showReminderNotification(
                            context = applicationContext,
                            reminder = reminder,
                            customerName = it.getFullName(),
                            customerPhone = it.mobileNumber,
                            policyType = policy?.policyType
                        )
                    }
                }
            }
            
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
