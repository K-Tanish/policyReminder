package com.policyreminder.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.policyreminder.app.data.dao.*
import com.policyreminder.app.data.model.*

@Database(
    entities = [
        Customer::class,
        FamilyMember::class,
        Policy::class,
        Reminder::class,
        CallLog::class,
        MessageTemplate::class,
        RenewalHistory::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun customerDao(): CustomerDao
    abstract fun familyMemberDao(): FamilyMemberDao
    abstract fun policyDao(): PolicyDao
    abstract fun reminderDao(): ReminderDao
    abstract fun callLogDao(): CallLogDao
    abstract fun messageTemplateDao(): MessageTemplateDao
    abstract fun renewalHistoryDao(): RenewalHistoryDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "policy_reminder_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
