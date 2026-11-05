package com.policyreminder.app

import android.app.Application
import com.policyreminder.app.data.database.AppDatabase
import com.policyreminder.app.utils.NotificationHelper
import com.policyreminder.app.utils.WorkManagerHelper

class PolicyReminderApplication : Application() {
    
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize notification channels
        NotificationHelper.createNotificationChannels(this)
        
        // Schedule periodic reminder checks
        WorkManagerHelper.scheduleReminderCheck(this)
    }
}
