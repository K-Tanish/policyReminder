package com.policyreminder.app.data.database

import androidx.room.TypeConverter
import com.policyreminder.app.data.model.*

class Converters {
    
    @TypeConverter
    fun fromPolicyStatus(value: PolicyStatus): String {
        return value.name
    }
    
    @TypeConverter
    fun toPolicyStatus(value: String): PolicyStatus {
        return try {
            PolicyStatus.valueOf(value)
        } catch (e: IllegalArgumentException) {
            PolicyStatus.ACTIVE
        }
    }
    
    @TypeConverter
    fun fromReminderType(value: ReminderType): String {
        return value.name
    }
    
    @TypeConverter
    fun toReminderType(value: String): ReminderType {
        return try {
            ReminderType.valueOf(value)
        } catch (e: IllegalArgumentException) {
            ReminderType.POLICY_RENEWAL
        }
    }
    
    @TypeConverter
    fun fromCallOutcome(value: CallOutcome): String {
        return value.name
    }
    
    @TypeConverter
    fun toCallOutcome(value: String): CallOutcome {
        return try {
            CallOutcome.valueOf(value)
        } catch (e: IllegalArgumentException) {
            CallOutcome.NO_RESPONSE
        }
    }
    
    @TypeConverter
    fun fromTemplateType(value: TemplateType): String {
        return value.name
    }
    
    @TypeConverter
    fun toTemplateType(value: String): TemplateType {
        return try {
            TemplateType.valueOf(value)
        } catch (e: IllegalArgumentException) {
            TemplateType.GENERAL
        }
    }
}
