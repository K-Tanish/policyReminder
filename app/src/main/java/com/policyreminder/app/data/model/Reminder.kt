package com.policyreminder.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val customerId: Long,
    val policyId: Long? = null, // Null for birthdays/anniversaries
    val familyMemberId: Long? = null, // For family member events
    val type: ReminderType,
    val reminderDate: Long,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val isSnoozed: Boolean = false,
    val snoozeUntil: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)

enum class ReminderType {
    POLICY_RENEWAL,
    BIRTHDAY,
    ANNIVERSARY,
    FOLLOW_UP_CALL
}
