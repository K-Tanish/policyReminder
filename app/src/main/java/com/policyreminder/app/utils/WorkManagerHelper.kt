package com.policyreminder.app.utils

import android.content.Context
import androidx.work.*
import com.policyreminder.app.worker.ReminderWorker
import java.util.concurrent.TimeUnit

object WorkManagerHelper {
    
    private const val REMINDER_WORK_NAME = "reminder_check_work"
    
    /**
     * Schedule periodic reminder check (runs daily)
     */
    fun scheduleReminderCheck(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()
        
        val reminderWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            24, TimeUnit.HOURS,  // Repeat every 24 hours
            15, TimeUnit.MINUTES // Flex interval of 15 minutes
        )
            .setConstraints(constraints)
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()
        
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            REMINDER_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            reminderWorkRequest
        )
    }
    
    /**
     * Schedule one-time reminder check (for immediate or specific time)
     */
    fun scheduleOneTimeReminderCheck(context: Context, delayMillis: Long = 0) {
        val reminderWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .build()
        
        WorkManager.getInstance(context).enqueue(reminderWorkRequest)
    }
    
    /**
     * Cancel all scheduled reminder checks
     */
    fun cancelReminderCheck(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(REMINDER_WORK_NAME)
    }
    
    /**
     * Calculate delay until next 9 AM (preferred notification time)
     */
    private fun calculateInitialDelay(): Long {
        val now = System.currentTimeMillis()
        val calendar = java.util.Calendar.getInstance().apply {
            timeInMillis = now
            set(java.util.Calendar.HOUR_OF_DAY, 9)
            set(java.util.Calendar.MINUTE, 0)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
            
            // If 9 AM has passed today, schedule for tomorrow
            if (timeInMillis <= now) {
                add(java.util.Calendar.DAY_OF_YEAR, 1)
            }
        }
        
        return calendar.timeInMillis - now
    }
    
    /**
     * Check if reminder work is scheduled
     */
    fun isReminderWorkScheduled(context: Context): Boolean {
        val workInfos = WorkManager.getInstance(context)
            .getWorkInfosForUniqueWork(REMINDER_WORK_NAME)
            .get()
        
        return workInfos.any { workInfo ->
            workInfo.state == WorkInfo.State.ENQUEUED || workInfo.state == WorkInfo.State.RUNNING
        }
    }
}
