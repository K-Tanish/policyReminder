package com.policyreminder.app.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.policyreminder.app.MainActivity
import com.policyreminder.app.R
import com.policyreminder.app.data.model.Reminder
import com.policyreminder.app.data.model.ReminderType

object NotificationHelper {
    
    private const val CHANNEL_ID_REMINDERS = "policy_reminders"
    private const val CHANNEL_ID_BIRTHDAYS = "birthdays"
    private const val CHANNEL_ID_GENERAL = "general"
    
    /**
     * Create notification channels (required for Android 8.0+)
     */
    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            // Policy Reminders Channel
            val reminderChannel = NotificationChannel(
                CHANNEL_ID_REMINDERS,
                "Policy Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for policy renewals and follow-ups"
                enableVibration(true)
                enableLights(true)
            }
            
            // Birthdays & Anniversaries Channel
            val birthdayChannel = NotificationChannel(
                CHANNEL_ID_BIRTHDAYS,
                "Birthdays & Anniversaries",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for customer birthdays and anniversaries"
                enableVibration(true)
            }
            
            // General Channel
            val generalChannel = NotificationChannel(
                CHANNEL_ID_GENERAL,
                "General",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "General app notifications"
            }
            
            notificationManager.createNotificationChannel(reminderChannel)
            notificationManager.createNotificationChannel(birthdayChannel)
            notificationManager.createNotificationChannel(generalChannel)
        }
    }
    
    /**
     * Show notification for a reminder
     */
    fun showReminderNotification(
        context: Context,
        reminder: Reminder,
        customerName: String,
        customerPhone: String,
        policyType: String? = null
    ) {
        val channelId = when (reminder.type) {
            ReminderType.POLICY_RENEWAL, ReminderType.FOLLOW_UP_CALL -> CHANNEL_ID_REMINDERS
            ReminderType.BIRTHDAY, ReminderType.ANNIVERSARY -> CHANNEL_ID_BIRTHDAYS
        }
        
        val title = when (reminder.type) {
            ReminderType.POLICY_RENEWAL -> "Policy Renewal Due"
            ReminderType.BIRTHDAY -> "Birthday Reminder"
            ReminderType.ANNIVERSARY -> "Anniversary Reminder"
            ReminderType.FOLLOW_UP_CALL -> "Follow-up Call"
        }
        
        val content = when (reminder.type) {
            ReminderType.POLICY_RENEWAL -> "$customerName - $policyType policy renewal"
            ReminderType.BIRTHDAY -> "Wish $customerName a happy birthday!"
            ReminderType.ANNIVERSARY -> "Wish $customerName a happy anniversary!"
            ReminderType.FOLLOW_UP_CALL -> "Follow up with $customerName"
        }
        
        // Intent to open app
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("reminderId", reminder.id)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            reminder.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        // Call action
        val callIntent = Intent(Intent.ACTION_DIAL).apply {
            data = android.net.Uri.parse("tel:$customerPhone")
        }
        val callPendingIntent = PendingIntent.getActivity(
            context,
            reminder.id.toInt() + 10000,
            callIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        
        // WhatsApp action
        val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
            val cleanNumber = customerPhone.replace(Regex("[^0-9]"), "")
            val fullNumber = if (cleanNumber.length == 10) "91$cleanNumber" else cleanNumber
            data = android.net.Uri.parse("https://wa.me/$fullNumber")
        }
        val whatsappPendingIntent = PendingIntent.getActivity(
            context,
            reminder.id.toInt() + 20000,
            whatsappIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        
        // Build notification
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setStyle(NotificationCompat.BigTextStyle().bigText(content))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(
                android.R.drawable.ic_menu_call,
                "Call",
                callPendingIntent
            )
            .addAction(
                android.R.drawable.ic_menu_send,
                "WhatsApp",
                whatsappPendingIntent
            )
            .build()
        
        // Show notification
        try {
            NotificationManagerCompat.from(context).notify(
                reminder.id.toInt(),
                notification
            )
        } catch (e: SecurityException) {
            // Handle case where notification permission is not granted
            e.printStackTrace()
        }
    }
    
    /**
     * Cancel a specific notification
     */
    fun cancelNotification(context: Context, notificationId: Int) {
        NotificationManagerCompat.from(context).cancel(notificationId)
    }
    
    /**
     * Cancel all notifications
     */
    fun cancelAllNotifications(context: Context) {
        NotificationManagerCompat.from(context).cancelAll()
    }
}
